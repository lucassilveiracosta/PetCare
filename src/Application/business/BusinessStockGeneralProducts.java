package business;

import business.interfaces.IBusinessStockGeneralProducts;

import exceptions.StockGeneralProductsConflictException;
import exceptions.StockGeneralProductsNotFoundException;
import business.model.notaFiscal.Produto;
import data.interfaces.IRepositoryStockGeneralProducts;

import java.util.ArrayList;

public class BusinessStockGeneralProducts implements IBusinessStockGeneralProducts {

    private final IRepositoryStockGeneralProducts repositoryStockGeneralProducts;

    public BusinessStockGeneralProducts(IRepositoryStockGeneralProducts repository) {
        this.repositoryStockGeneralProducts = repository;
    }

    public Produto getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Produto produto = repositoryStockGeneralProducts.findById(id);

        if (produto == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        return produto;
    }

    public ArrayList<Produto> getAll() {
        return repositoryStockGeneralProducts.findAll();
    }

    public void patch(int id, Produto novoProduto) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        if (novoProduto == null) throw new IllegalArgumentException("Product can't be null");

        Produto existente = repositoryStockGeneralProducts.findById(id);
        if (existente == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStockGeneralProducts.update(id, novoProduto);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");

        Produto produto = repositoryStockGeneralProducts.findById(id);
        if (produto == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStockGeneralProducts.remove(produto);
    }

    public void post(Produto novoProduto) {
        if (novoProduto == null) throw new IllegalArgumentException("Product can't be null");

        Produto exists = repositoryStockGeneralProducts.findById(novoProduto.getId());

        if (exists != null) {
            throw new StockGeneralProductsConflictException("This product already exists");
        }

        repositoryStockGeneralProducts.create(novoProduto);
    }
}