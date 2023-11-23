package model;

import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean createCustomer(CustomerDTO customerDTO);
    boolean updateCustomer(CustomerDTO customerDTO);
    boolean deleteCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    CustomerDTO searchCustomer(String id);
}
