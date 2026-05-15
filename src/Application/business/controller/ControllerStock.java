package business.controller;

import business.interfaces.IControllerStock;

import business.model.animal.Animal;
import data.interfaces.IRepositoryStock;
import exceptions.StockGeneralProductsConflictException;
import exceptions.StockGeneralProductsNotFoundException;
import business.model.notaFiscal.Produto;

import java.util.ArrayList;

public class ControllerStock implements IControllerStock {

    private final IRepositoryStock repositoryStock;

    public ControllerStock(IRepositoryStock repository) {
        this.repositoryStock = repository;
    }

    public Produto getById(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        Produto product = repositoryStock.findById(id);

        if (product == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        return product;
    }

    public ArrayList<Produto> getAll() {
        return repositoryStock.findAll();
    }

    @Override
    public void patch(int id, Animal partialData) {

    }

    public void put(int id, Produto newProduct) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");

        Produto exists = repositoryStock.findById(id);
        if (exists == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.update(id, newProduct);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("400 - ID must be positive");

        Produto product = repositoryStock.findById(id);
        if (product == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStock.remove(product);
    }

    public void post(Produto newProduct) {
        if (newProduct == null) throw new IllegalArgumentException("400 - Product can't be null");

        Produto exists = repositoryStock.findById(newProduct.getId());

        if (exists != null) {
            throw new StockGeneralProductsConflictException("409 - This product already exists");
        }

        repositoryStock.create(newProduct);
    }
}