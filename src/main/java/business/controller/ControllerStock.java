package business.controller;

import business.interfaces.IControllerStock;

import data.interfaces.IRepositoryStock;
import exceptions.AppointmentNotFoundException;
import exceptions.StockGeneralProductsConflictException;
import exceptions.StockGeneralProductsNotFoundException;
import business.model.invoice.Product;

import java.util.ArrayList;

public class ControllerStock implements IControllerStock {

    private final IRepositoryStock repositoryStock;

    public ControllerStock(IRepositoryStock repository) {
        this.repositoryStock = repository;
    }

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
    }

    public void put(int id, Product newProduct) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");

        Product exists = repositoryStock.findById(id);
        if (exists == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.update(id, newProduct);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Product product = repositoryStock.findById(id);
        if (product == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.remove(product);
    }

    public void post(Product newProduct) {
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");

        Product exists = repositoryStock.findById(newProduct.getId());

        if (exists != null) {
            throw new StockGeneralProductsConflictException("409 - This product already exists");
        }

        repositoryStock.create(newProduct);
    }
}