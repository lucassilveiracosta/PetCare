package business.controller;

import business.interfaces.IControllerStock;

import data.SaveData;
import data.interfaces.IRepositoryStock;
import enums.MedicineType;
import exceptions.AppointmentNotFoundException;
import exceptions.InsufficientStockException;
import exceptions.PrescriptionRequiredException;
import exceptions.StockGeneralProductsConflictException;
import exceptions.StockGeneralProductsNotFoundException;
import business.model.invoice.Product;

import java.util.ArrayList;
import java.util.List;

public class ControllerStock implements IControllerStock {

    private final IRepositoryStock repositoryStock;

    public ControllerStock(IRepositoryStock repository) {
        this.repositoryStock = repository;
    }

    @Override
    public Product getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Product product = repositoryStock.findById(id);

        if (product == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        return product;
    }

    public ArrayList<Product> getAll() {
        return repositoryStock.findAll();
    }

    @Override
    public void patch(int id, Product partialData) {

        if (partialData == null) throw new IllegalArgumentException("400 - Appointment can't be null");

        Product exists = repositoryStock.findById(id);
        if (exists == null) {
            throw new AppointmentNotFoundException("404 - Appointment with ID " + id + " not found");
        }

        if (partialData.getPrice() < 0.0) {
            exists.setPrice(partialData.getPrice());
        }

        if (partialData.getDescription() != null && !partialData.getDescription().isBlank()) {
            exists.setDescription(partialData.getDescription());
        }

        if (partialData.getQuantity() > 0 ) {
            exists.setQuantity(partialData.getQuantity());
        }


        int index = repositoryStock.findAll().indexOf(exists);
        repositoryStock.update(index, exists);
        persist();
    }

    @Override
    public void put(int id, Product newProduct) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");
        Product exists = repositoryStock.findById(id);
        if (exists == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.update(id, newProduct);
        persist();
    }

    @Override
    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Product product = repositoryStock.findById(id);
        if (product == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.remove(product);
        persist();
    }

    @Override
    public void post(Product newProduct) {
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");

        Product exists = repositoryStock.findById(newProduct.getId());

        if (exists != null) {
            throw new StockGeneralProductsConflictException("409 - This product already exists");
        }

        repositoryStock.create(newProduct);
        persist();
    }


    @Override
    public void registerSale(Product product, int quantity, boolean hasPrescription) {
        if (product == null) throw new IllegalArgumentException("400 - Product can't be null");
        if (quantity <= 0) throw new IllegalArgumentException("400 - Quantity must be positive");

        Product stored = repositoryStock.findById(product.getId());
        if (stored == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        // cannot bill more units than there are in stock
        if (quantity > stored.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for '" + stored.getName()
                    + "': requested " + quantity + ", available " + stored.getQuantity() + ".");
        }
        // controlled medicine cannot be sold without a linked prescription
        if (stored.isVet() && stored.getMedicineType() == MedicineType.CONTROLLED && !hasPrescription) {
            throw new PrescriptionRequiredException("Blocked sale: '" + stored.getName()
                    + "' is a controlled medicine and requires a recipe.");
        }

        stored.setQuantity(stored.getQuantity() - quantity);
        persist();
    }

    private void persist() {
        new SaveData().saveAllProducts(repositoryStock.findAll());
    }


    public List<Product> filterByMedicineType(MedicineType medicineType) {
        List<Product> filter = new ArrayList<>();

        for (Product p: repositoryStock.findAll()) {
            if (p.isVet()) {
                filter.add(p);
            }
        }
        return filter;
    }

    public List<Product> filterVeterinarianProducts() {
        List<Product> filter = new ArrayList<>();

        for (Product p: repositoryStock.findAll()) {
            if (p.isVet()) {
                filter.add(p);
            }
        }
        return filter;
    }

    public List<Product> filterPetShopProducts() {
        List<Product> filter = new ArrayList<>();

        for (Product p: repositoryStock.findAll()) {
            if (!p.isVet()) {
                filter.add(p);
            }
        }
        return filter;
    }
}