package business;

import exceptions.StockGeneralProductsConflictException;
import exceptions.StockGeneralProductsNotFoundException;
import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStockGeneralProducts;

import java.util.ArrayList;

public class BusinessStockGeneralProducts {

    private IRepositoryStockGeneralProducts repositoryStockGeneralProducts;

    public Produto getById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Produto nf = repositoryStockGeneralProducts.findById(id);

        if (nf == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        return nf;
    }

    public ArrayList<Produto> getAll() {
        return repositoryStockGeneralProducts.findAll();
    }

    public void patch(int id, Produto nf) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        if (nf == null) throw new IllegalArgumentException("Invoice can't be null");
        Produto produtos = repositoryStockGeneralProducts.findById(id);
        if (produtos == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStockGeneralProducts.update(id, nf);
    }

    public void delete(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must be positive");
        Produto produtos = repositoryStockGeneralProducts.findById(id);
        if (produtos == null) throw new StockGeneralProductsNotFoundException("404 - ID not found");

        repositoryStockGeneralProducts.remove(produto);
    }

    public void post(Produto nf) {
        Produto exists = repositoryStockGeneralProducts.findById(nf.getId());
        if (nf == exists) throw new StockGeneralProductsConflictException("This invoice already exists");
        repositoryStockGeneralProducts.create(nf);
    }
}
