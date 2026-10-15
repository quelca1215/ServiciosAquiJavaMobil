package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";

    private String currentCategory = "Todas";
    private List<ProfessionalModel> originalList = new ArrayList<>();
    private List<ProfessionalModel> currentFilteredList = new ArrayList<>();

    private ProfessionalAdapter adapter;
    private RecyclerView rvProfessionals;
    private View layoutEmpty;
    private TextView tvTitle;
    private TextView tvResultsCount;
    private ImageButton btnToggleView;
    private LinearLayout containerActiveChips;
    private String searchQuery = "";

    // Banderas de Filtros Activos
    private boolean isNearFilter = false;
    private boolean isTopFilter = false;
    private boolean isAvailableFilter = false;
    private boolean isBudgetFilter = false;

    private boolean isGridMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalog);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent() != null && getIntent().hasExtra(EXTRA_CATEGORY_NAME)) {
            currentCategory = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        }

        initViews();
        loadData();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBackCatalog);
        btnBack.setOnClickListener(v -> finish());

        btnToggleView = findViewById(R.id.btnToggleView);
        btnToggleView.setOnClickListener(v -> toggleLayoutMode());

        tvTitle = findViewById(R.id.tvCatalogTitle);
        tvResultsCount = findViewById(R.id.tvResultsCount);
        containerActiveChips = findViewById(R.id.containerActiveChips);

        if (currentCategory != null && !currentCategory.equalsIgnoreCase("Todas")) {
            tvTitle.setText("Especialistas en " + currentCategory);
        } else {
            tvTitle.setText("Todos los Especialistas");
        }

        rvProfessionals = findViewById(R.id.rvCatalogProfessionals);
        layoutEmpty = findViewById(R.id.layoutEmptyCatalog);
        EditText etSearch = findViewById(R.id.etSearchCatalog);

        // BOTÓN TUNE APERTURA MODAL FILTROS
        ImageButton btnFilterTune = findViewById(R.id.btnFilterTune);
        btnFilterTune.setOnClickListener(v -> openFilterModal());

        rvProfessionals.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProfessionalAdapter(new ArrayList<>(), model -> {
            ProviderDetailDialog dialog = ProviderDetailDialog.newInstance(model);
            dialog.show(getSupportFragmentManager(), "ProviderDetailDialog");
        });

        rvProfessionals.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString();
                applyAllFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void openFilterModal() {
        FilterDialog dialog = FilterDialog.newInstance(isNearFilter, isTopFilter, isAvailableFilter, isBudgetFilter);
        dialog.setListener((near, top, available, budget) -> {
            isNearFilter = near;
            isTopFilter = top;
            isAvailableFilter = available;
            isBudgetFilter = budget;
            applyAllFilters();
        });
        dialog.show(getSupportFragmentManager(), "FilterDialog");
    }

    private void toggleLayoutMode() {
        isGridMode = !isGridMode;
        adapter.setGridView(isGridMode);

        if (isGridMode) {
            rvProfessionals.setLayoutManager(new GridLayoutManager(this, 2));
            btnToggleView.setImageResource(R.drawable.ic_list_view);
        } else {
            rvProfessionals.setLayoutManager(new LinearLayoutManager(this));
            btnToggleView.setImageResource(R.drawable.ic_grid_view);
        }
    }

    private void loadData() {
        originalList = MockDataProvider.getProfessionalsByCategory(currentCategory);
        applyAllFilters();
    }

    private void applyAllFilters() {
        List<ProfessionalModel> filtered = new ArrayList<>();

        for (ProfessionalModel p : originalList) {
            boolean matchesSearch = true;
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String lower = searchQuery.toLowerCase().trim();
                matchesSearch = p.getName().toLowerCase().contains(lower) ||
                                p.getTitle().toLowerCase().contains(lower) ||
                                p.getCategory().toLowerCase().contains(lower);
            }

            boolean matchesNear = !isNearFilter || p.getDistanceKm() <= 2.0;
            boolean matchesTop = !isTopFilter || p.getRating() >= 4.8;
            boolean matchesAvailable = !isAvailableFilter || p.getStatusBadge().contains("Disponible");
            boolean matchesBudget = !isBudgetFilter || p.getPriceText().contains("40") || p.getPriceText().contains("35");

            if (matchesSearch && matchesNear && matchesTop && matchesAvailable && matchesBudget) {
                filtered.add(p);
            }
        }

        currentFilteredList = filtered;
        updateUIList();
        updateActiveChipsUI();
    }

    private void updateActiveChipsUI() {
        if (containerActiveChips == null) return;
        containerActiveChips.removeAllViews();

        if (isNearFilter) {
            addChipView("Más Cercanos (< 2 km)", () -> {
                isNearFilter = false;
                applyAllFilters();
            });
        }

        if (isTopFilter) {
            addChipView("Calificación 4.8+", () -> {
                isTopFilter = false;
                applyAllFilters();
            });
        }

        if (isAvailableFilter) {
            addChipView("Disponibles Hoy", () -> {
                isAvailableFilter = false;
                applyAllFilters();
            });
        }

        if (isBudgetFilter) {
            addChipView("Tarifa Económica", () -> {
                isBudgetFilter = false;
                applyAllFilters();
            });
        }
    }

    private void addChipView(String label, Runnable onRemove) {
        View chipView = LayoutInflater.from(this).inflate(R.layout.item_active_filter_chip, containerActiveChips, false);
        TextView tvLabel = chipView.findViewById(R.id.tvChipLabel);
        View btnRemove = chipView.findViewById(R.id.btnRemoveChip);

        if (tvLabel != null) tvLabel.setText(label);
        if (btnRemove != null) {
            btnRemove.setOnClickListener(v -> onRemove.run());
        }

        containerActiveChips.addView(chipView);
    }

    private void updateUIList() {
        adapter.updateList(currentFilteredList);
        if (tvResultsCount != null) {
            tvResultsCount.setText("Mostrando " + currentFilteredList.size() + " especialistas encontrados");
        }

        if (currentFilteredList.isEmpty()) {
            rvProfessionals.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            rvProfessionals.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
    }
}