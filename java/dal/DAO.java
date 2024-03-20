/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Account;
import model.AccountImage;
import model.AccountInfo;
import model.Category;
import model.Product;

/**
 *
 * @author khiem
 */
public class DAO extends DBContext {

    //upload
    public int upload(String firstname, InputStream input) {

        String sql = "INSERT INTO [dbo].[Profile]\n"
                + "           ([first_name]\n"
                + "           ,[photo])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, firstname);
            st.setBlob(2, input);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }

    //up
    public String up() {
        String sql = "SELECT [first_name]\n"
                + "      ,[photo]\n"
                + "  FROM [dbo].[Profile]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name");
            }
        } catch (Exception e) {
        }
        return null;
    }

    //img
    public byte[] array() {
        String sql = "SELECT [first_name]\n"
                + "      ,[photo]\n"
                + "  FROM [dbo].[Profile]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getBytes("photo");
            }
        } catch (Exception e) {
        }
        return null;
    }

    //--------------get All category
    public List<Category> getAll() {
        List<Category> list = new ArrayList<Category>();
        String sql = "select * from Categories";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c;
                c = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("describe"));
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }
    //----------
    public boolean checkCategoryToDelete(int cid){
        List<Product> list = new ArrayList<Product>();
        String sql = "select * from products where cid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    //-----------------get ALL product
    public List<Product> getAllProduct() {
        DAOCart dc = new DAOCart();
        List<Product> list = new ArrayList<Product>();
        String sql = "select * from products";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    //Productdetails the same product
    public List<Product> getTheSameProduct(int id) {
        Product pro=getProductsBYid(id);
        DAOCart dc = new DAOCart();
        List<Product> list = new ArrayList<Product>();
        String sql = "select * from products where cid=? ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pro.getCategory().getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    //----------get category
    public Category getCategoryById(int id) {
        String sql = "select * from Categories where id=?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Category c;
                c = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("describe"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    //---------get product by pid
    public Product getProductsBYid(int id) {
        //String sql="select * from products where cid=?";
        DAOCart dc = new DAOCart();
        String sql = "SELECT [ID]\n"
                + "      ,[name]\n"
                + "      ,[price]\n"
                + "      ,[describe]\n"
                + "      ,[image]\n"
                + "      ,[cid]\n"
                + "      ,[details]\n"
                + "  FROM [dbo].[products] where 1=1 ";
        if (id != 0) {
            sql += " and id=" + id;
        }
        Product p = new Product();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setDetails(rs.getString("details"));
            }
        } catch (SQLException e) {
        }
        return p;

    }

    //--------doc tu bang product theo cid
    public List<Product> getProductsBYCid(int cid) {
        List<Product> list = new ArrayList<>();
        //String sql="select * from products where cid=?";
        DAOCart dc = new DAOCart();
        String sql = "SELECT [ID]\n"
                + "      ,[name]\n"
                + "      ,[price]\n"
                + "      ,[describe]\n"
                + "      ,[image]\n"
                + "      ,[cid]\n"
                + "      ,[details]\n"
                + "  FROM [dbo].[products] where 1=1 ";
        if (cid != 0) {
            sql += " and cid=" + cid;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setCategory(c);
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }

        return list;
    }

    //---------------get product after filter
    public List<Product> getProductsAfterFilter(int cid, String details) {
        DAOCart dc = new DAOCart();
        List<Product> list = new ArrayList<>();
        //String sql="select * from products where cid=?";
        String[] detailz = details.split(":");
        String[] detail1 = detailz[0].split(",");
        String[] detail2 = detailz[1].split(",");
        String[] detail3 = detailz[2].split(",");
        String[] detail4 = detailz[4].split(",");

        String sql = "select p.ID,p.name,p.price,p.describe,p.image,p.cid,p.details "
                + "from products p inner join Categories c on c.ID=p.cid where 1=1 ";
        //---------cid phan category
        if (cid != 0) {
            sql += " and cid=" + cid;
        }
        //-------------gold
        int dem = 0;
        for (String i : detail1) {
            if (!i.equals("null")) {
                dem++;
            }
        }
        if (dem > 0) {
            sql += "  and (";
            dem = 0;
            for (String i : detail1) {
                if (i.equalsIgnoreCase("null")) {
                    continue;
                } else if (dem > 0) {
                    sql += " or p.details like '%" + i + "%'";
                    dem++;
                } else if (dem == 0) {
                    sql += "p.details like '%" + i + "%'";
                    dem++;
                }
            }
            sql += ")";
        }
        //--------small
        dem = 0;
        for (String i : detail2) {
            if (!i.equals("null")) {
                dem++;
            }
        }
        if (dem > 0) {
            sql += "  and (";
            dem = 0;
            for (String i : detail2) {
                if (i.equalsIgnoreCase("null")) {
                    continue;
                } else if (dem > 0) {
                    sql += " or p.details like '%" + i + "%'";
                    dem++;
                } else if (dem == 0) {
                    sql += "p.details like '%" + i + "%'";
                    dem++;
                }
            }
            sql += ")";
        }
        //--------classic
        dem = 0;
        for (String i : detail3) {
            if (!i.equals("null")) {
                dem++;
            }
        }
        if (dem > 0) {
            sql += "  and (";
            dem = 0;
            for (String i : detail3) {
                if (i.equalsIgnoreCase("null")) {
                    continue;
                } else if (dem > 0) {
                    sql += " or p.details like '%" + i + "%'";
                    dem++;
                } else if (dem == 0) {
                    sql += "p.details like '%" + i + "%'";
                    dem++;
                }
            }
            sql += ")";
        }
        //---------price filter
        dem = 0;
        for (String i : detail4) {
            if (!i.equals("null")) {
                dem++;
            }
        }
        if (dem > 0) {
            sql += "  and (";
            dem = 0;
            for (String i : detail4) {
                if (i.equalsIgnoreCase("null")) {
                    continue;
                } else if (dem > 0) {
                    sql += " and p.price< " + i;
                    dem++;
                } else if (dem == 0) {
                    sql += "p.price>" + i;
                    dem++;
                }
            }
            sql += ")";
        }
         sql += " and p.details like '%" + detailz[3] + "%'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setCategory(c);
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }

        return list;
    }

    //---------------get product by name
    public List<Product> getProductsByName(String pName) {
        List<Product> list = new ArrayList<>();
        DAOCart dc = new DAOCart();
        String sql = "SELECT [ID]\n"
                + "      ,[name]\n"
                + "      ,[price]\n"
                + "      ,[describe]\n"
                + "      ,[image]\n"
                + "      ,[cid]\n"
                + "      ,[details]\n"
                + "  FROM [dbo].[products] where 1=1 ";
        if (pName != null) {
            sql += " and name like '%" + pName + "%'";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            //st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setRating(dc.getRating(rs.getString("id")));
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    //update ACCOUNT STATUS
    public int updateAccountStatus(Account a, boolean status) {
        String sql = "update Account set status=? where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, status);
            st.setInt(2, a.getAid());
            return st.executeUpdate();

        } catch (SQLException e) {
        }
        return 0;

    }
    //update ACCOUNT STATUS
    public int updateAccountRole(Account a, int i) {
        String sql = "update Account set role=? where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, i);
            st.setInt(2, a.getAid());
            return st.executeUpdate();

        } catch (SQLException e) {
        }
        return 0;

    }

    //login
    public Account checkLogin(String username, String password) {
        String sql = "select * from Account where username=? and password=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("aid"), username, password, rs.getInt("role"), rs.getBoolean("status"));
            }

        } catch (SQLException e) {
        }
        return null;

    }

    //GET NUMBER OF ACCOUNT
    public int getAid() {
        String sql = "select count(*)  as count from Account";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //LIST ALL ACCOUNT
    public List<Account> getAllAccount() {
        DAOCart dc = new DAOCart();
        String sql = "select * from Account ";
        List<Account> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getInt("aid"), rs.getString("username"), rs.getString("password"), rs.getInt("role"), rs.getBoolean("status"));
                a.setMoney(dc.getSpendingMoneyFromAccount(rs.getInt("aid")));
                list.add(a);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    //LIST ACCOUNT by NAME
    public List<Account> getAllAccountbyName(String s) {
        DAOCart dc = new DAOCart();
        String sql = "select * from Account where [username] like '%" + s + "%'";
        List<Account> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account a = new Account(rs.getInt("aid"), rs.getString("username"), rs.getString("password"), rs.getInt("role"), rs.getBoolean("status"));
                a.setMoney(dc.getSpendingMoneyFromAccount(rs.getInt("aid")));
                list.add(a);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    //create account
    public Account signUp(String username, String password) {
        String sql = "insert into Account values\n"
                + "(?,?,?,1,1)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, Integer.toString(getAid() + 1));
            st.setString(2, username);
            st.setString(3, password);
            st.executeUpdate();

        } catch (SQLException e) {
        }
        return checkLogin(username, password);
    }

    //UPDATE PROFILE ACCOUNT
    public int updateAccount(String password, Account a) {
        String sql = "update Account set  password=? where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, password);
            st.setInt(2, a.getAid());

            return st.executeUpdate();

        } catch (SQLException e) {
        }
        return 0;
    }

    //UPDATE PROFILE ACCOUNT INFO
    public int updateAccountInfo(String fullname, String email, String phone, String address, AccountInfo ai, int age, boolean gender) {
        String sql = "update AccountInfo set  fullname=?,email=?,phone=?,address=?,age=?,gender=? where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, fullname);
            st.setString(2, email);
            st.setString(3, phone);
            st.setString(4, address);
            st.setInt(7, ai.getAid());
            st.setInt(5, age);
            st.setBoolean(6, gender);

            return st.executeUpdate();

        } catch (SQLException e) {
        }
        return 0;
    }

    //GET ACCOUNT TO UPDATE PROFILE
    public Account getAccount(String username, String password) {
        String sql = "select * from Account where username=? and password=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("aid"), rs.getString("username"), rs.getString("password"), rs.getInt("role"), rs.getBoolean("status"));
            }

        } catch (SQLException e) {
        }
        return null;
    }

    //GET ACCOUNT FROM ACCOUNT ID
    public Account getAccountFromAid(int aid) {
        DAOCart dc = new DAOCart();
        String sql = "select * from Account where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Account a = new Account(rs.getInt("aid"), rs.getString("username"), rs.getString("password"), rs.getInt("role"), rs.getBoolean("status"));
                a.setMoney(dc.getSpendingMoneyFromAccount(rs.getInt("aid")));
                return a;
            }

        } catch (SQLException e) {
        }
        return null;
    }

    //GET ACCOUNT INFO FROM ACCOUNT
    public AccountInfo getAccountInfo(Account a) {
        String sql = "select * from AccountInfo where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, a.getAid());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new AccountInfo(rs.getInt("aid"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getInt("age"), rs.getBoolean("gender"));
            }

        } catch (SQLException e) {
        }
        return null;
    }

    //create an ACCOUNT INFO and return this ACCOUNT INFO
    public AccountInfo createAccountInfo(String fullname, String email, String phone, String address, Account a, int age, boolean gender) {
        String sql = "insert into AccountInfo values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(2, fullname);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, address);
            st.setInt(6, age);
            st.setBoolean(7, gender);
            st.setInt(1, a.getAid());

            st.executeUpdate();

        } catch (SQLException e) {
        }
        return getAccountInfo(a);
    }

    //UPDATE PROFILE ACCOUNT INFO
    public int updateAccountImage(Account a, InputStream input) {
        String sql = "update AccountImage set  image=? where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(2, a.getAid());
            st.setBlob(1, input);
            return st.executeUpdate();

        } catch (SQLException e) {
        }
        return 0;
    }

    //GET ACCOUNT INFO FROM ACCOUNT
    public AccountImage getAccountImage(Account a) {
        String sql = "select * from AccountImage where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, a.getAid());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new AccountImage(a.getAid(), rs.getBlob("image"));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    //add ACCOUNT IMAGE
    public AccountImage createAccountImage(Account a, InputStream input) {
        String sql = "insert into AccountImage values(?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, a.getAid());
            st.setBlob(2, input);
            st.executeUpdate();

        } catch (SQLException e) {
        }
        return getAccountImage(a);
    }
    //CRUD
    //ADD CATEGORY
    public Category addCategory(int id,String name,String describe){
        String sql = "INSERT INTO Categories values(?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, describe);
            st.executeUpdate();

        } catch (SQLException e) {
        }

        return getCategoryById(id);
    }
    //ADD product
    public Product addProduct(int id, String name, int money, String describe, String image, int cid, String details) {
        String sql = "INSERT INTO products values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, name);
            st.setInt(3, money);
            st.setString(4, describe);
            st.setString(5, image);
            st.setInt(6, cid);
            st.setString(7, details);
            st.executeUpdate();

        } catch (SQLException e) {
        }

        return getProductsBYid(id);
    }
    //UPDATE category
    public Category updateCategory(int id,String name,String describe){
        String sql="update Categories set name = ?,describe = ? where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, describe);
            st.setInt(3, id);
            st.executeUpdate();

        } catch (SQLException e) {
        }
        
        return getCategoryById(id);
    }

    //UPDATE product
    public Product updateProduct(String id, String name, int money, String describe, String image, int cid, String details) {
        String sql = "UPDATE [dbo].[products]\n"
                + "   SET [name] = ?,[price] = ?,[describe] = ?,[image] = ?,[cid] = ?,[details] = ?\n"
                + " WHERE id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(7, id);
            st.setString(1, name);
            st.setInt(2, money);
            st.setString(3, describe);
            st.setString(4, image);
            st.setInt(5, cid);
            st.setString(6, details);
            st.executeUpdate();

        } catch (SQLException e) {
        }
        int pid = 0;
        try {
            pid = Integer.parseInt(id);
        } catch (Exception e) {
        }
        return getProductsBYid(pid);
    }
    //DELETE category
    public boolean deleteCategory(int id) {
        String sql = "delete from Categories where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    //DELETE product
    public boolean deleteProduct(String id) {
        String sql1="delete from Ratings where pid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql1);
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
        }
        String sql = "delete from products where id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }
    

    //get max id
    public int getMaxId() {
        String sql = "select id as max from products";
        List<Integer> a = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                a.add(rs.getInt("max"));
            }
        } catch (SQLException e) {
        }
        return max(a);
    }
    //get max id
    public int getMaxIdCategory() {
        String sql = "select max(id) as id from Categories";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    //max
    public int max(List<Integer> a) {
        int z = 0;
        for (Integer i : a) {
            if (i > z) {
                z = i;
            }
        }
        return z;
    }

    //-----------------get ALL product
    public List<Product> getNewProduct() {
        DAOCart dc = new DAOCart();
        List<Product> list = new ArrayList<Product>();
        String sql = "select * from products";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setSale(dc.getProductSale(rs.getString("id")));
                p.setRating(dc.getRating(rs.getString("id")));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return bubbleSort(list, list.size(),3);
    }

    //new product
    public List<Product> bubbleSort(List<Product> arr, int n,int sort) {
        int i, j;
        boolean swapped;
        switch (sort) {
            //price 
            case 1:
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getPrice() > arr.get(j + 1).getPrice()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            case 2:
                //price
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getPrice() < arr.get(j + 1).getPrice()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            case 4:
                //sale
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getSale()< arr.get(j + 1).getSale()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            case 6:
                //low sale
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getSale()>= arr.get(j + 1).getSale()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            case 3:
                //new
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        int a = 0, b = 0;
                        try {
                            a = Integer.parseInt(arr.get(j).getId());
                            b = Integer.parseInt(arr.get(j + 1).getId());
                        } catch (Exception e) {
                        }
                        if (a <= b) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }       break;
            case 5:
                //rating
                 for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getRating()<= arr.get(j + 1).getRating()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                     }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            case 7:
                //low rating
                 for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getRating()> arr.get(j + 1).getRating()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                     }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
            default:
                break;
        }
        return arr;
    }
    public List<Account> bubbleSortAccount(List<Account> arr, int n,int sort) {
        int i, j;
        boolean swapped;
        switch (sort) {
            case 1:
            for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getMoney()<= arr.get(j + 1).getMoney()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
                case 2:
                for (i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (j = 0; j < n - i - 1; j++) {
                        if (arr.get(j).getMoney()> arr.get(j + 1).getMoney()) {
                            Collections.swap(arr, j, j + 1);
                            swapped = true;
                        }
                    }
                    
                    // If no two elements were swapped by inner loop,
                    // then break
                    if (swapped == false) {
                        break;
                    }
                }   break;
        default:
                break;
        }
        return arr;
    }
}
