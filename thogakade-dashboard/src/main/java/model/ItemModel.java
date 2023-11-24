package model;

import dto.CustomerDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean createItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;
    List<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
}
