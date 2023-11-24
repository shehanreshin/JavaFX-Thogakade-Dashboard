package model.impl;

import db.DBConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import model.CustomerModel;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {

    @Override
    public boolean createItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,itemDTO.getCode());
        preparedStatement.setString(2,itemDTO.getDescription());
        preparedStatement.setDouble(3,itemDTO.getUnitPrice());
        preparedStatement.setInt(4,itemDTO.getQtyOnHand());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET code=?, description=?, unitPrice=? WHERE qtyOnHand=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,itemDTO.getCode());
        preparedStatement.setString(2,itemDTO.getDescription());
        preparedStatement.setDouble(3,itemDTO.getUnitPrice());
        preparedStatement.setInt(4,itemDTO.getQtyOnHand());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from item WHERE code=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,code);
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> dtoList = new ArrayList<>();
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            dtoList.add(new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return dtoList;
    }
}
