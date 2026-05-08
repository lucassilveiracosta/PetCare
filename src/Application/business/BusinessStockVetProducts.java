package business;

import exceptions.StockVetProductsConflictException;
import exceptions.StockVetProductsNotFoundException;
import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStockVetProducts;

import java.util.ArrayList;

public class BusinessStockVetProducts {

    private IRepositoryStockVetProducts repositoryStockVetProducts;

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
        Produto produtos = repositoryStockVetProducts.findById(id);
        if (produtos == null) throw new StockVetProductsNotFoundException("404 - ID not found");

        repositoryStockVetProducts.update(id, nf);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Produto produtos = repositoryStockVetProducts.findById(id);
        if (produtos == null) throw new StockVetProductsNotFoundException("404 - ID not found");

        repositoryStockVetProducts.remove(produto);
    }

    public void post(Produto nf) {
        Produto exists = repositoryStockVetProducts.findById(nf.getId());
        if (nf == exists) throw new StockVetProductsConflictException("This invoice already exists");
        repositoryStockVetProducts.create(nf);
    }
}
