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
    public boolean createCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,customerDTO.getId());
        preparedStatement.setString(2,customerDTO.getName());
        preparedStatement.setString(3,customerDTO.getAddress());
        preparedStatement.setDouble(4,customerDTO.getSalary());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,customerDTO.getName());
        preparedStatement.setString(2,customerDTO.getAddress());
        preparedStatement.setDouble(3,customerDTO.getSalary());
        preparedStatement.setString(4,customerDTO.getId());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from customer WHERE id=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
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
