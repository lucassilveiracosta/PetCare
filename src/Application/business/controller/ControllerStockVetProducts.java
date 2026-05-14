package business.controller;

import business.interfaces.IControllerStockVetProducts;

import exceptions.StockVetProductsConflictException;
import exceptions.StockVetProductsNotFoundException;
import business.model.notaFiscal.Produto;
import data.interfaces.IRepositoryStockVetProducts;

import java.util.ArrayList;

public class ControllerStockVetProducts implements IControllerStockVetProducts {

    private final IRepositoryStockVetProducts repositoryStockVetProducts;

    public ControllerStockVetProducts(IRepositoryStockVetProducts repository) {
        this.repositoryStockVetProducts = repository;
    }

    public Produto getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Produto nf = repositoryStockVetProducts.findById(id);

        if (nf == null) throw new StockVetProductsNotFoundException("404 - ID not found");

        return nf;
    }

    public ArrayList<Produto> getAll() {
        return repositoryStockVetProducts.findAll();
    }

    public void patch(int id, Produto nf) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        if (nf == null) throw new IllegalArgumentException("Invoice can't be null");

        Produto existente = repositoryStockVetProducts.findById(id);
        if (existente == null) throw new StockVetProductsNotFoundException("404 - ID not found");

        repositoryStockVetProducts.update(id, nf);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");

        Produto produto = repositoryStockVetProducts.findById(id);
        if (produto == null) throw new StockVetProductsNotFoundException("404 - ID not found");

        repositoryStockVetProducts.remove(produto);
    }

    public void post(Produto nf) {
        if (nf == null) throw new IllegalArgumentException("Product cannot be null");

        Produto exists = repositoryStockVetProducts.findById(nf.getId());
        if (exists != null) throw new StockVetProductsConflictException("This invoice already exists");

        repositoryStockVetProducts.create(nf);
    }
}
