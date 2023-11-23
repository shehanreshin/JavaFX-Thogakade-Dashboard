package model.impl;

import db.DBConnection;
import dto.CustomerDTO;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {

    @Override
    public boolean createCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> dtoList = new ArrayList<>();
        String sql = "select * from customer";

        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);

        while (resultSet.next()) {
            dtoList.add(new CustomerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }

        return dtoList;
    }

    @Override
    public CustomerDTO searchCustomer(String id) {
        return null;
    }
}
