/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author khiem
 */
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Account;
import model.AccountImage;
import model.Cart;
import model.Category;
import model.Chart;
import model.Product;
import model.Rating;
import model.Transaction;

public class DAOCart extends DBContext {

    //ADD to Cart
    public int addToCart(String pid, int number, int aid) {

        String sql = "insert into Cart values (?,?,?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            st.setString(2, pid);
            st.setInt(3, number);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
    //UPdate Cart
    public int updateToCart(String pid, int number, int aid) {

        String sql = "update Cart set number+=? where pid=? and aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, number);
            st.setString(2, pid);
            st.setInt(3, aid);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;
    }
    //cartcheck
    public boolean checkCartExist(String pid,int aid){
        String sql="select * from Cart where pid=? and aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid);
            st.setInt(2, aid);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    //get Products from Cart
    public List<Cart> getFromCart(int aid) {
        String sql = "select c.pid,c.number,p.name,p.price,p.image from Cart c join products p on c.pid=p.ID where aid=?";
        List<Cart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Cart(rs.getInt("price"), rs.getString("pid"), rs.getInt("number"), rs.getString("name"), rs.getString("image")));
            }
        } catch (Exception e) {
        }
        return list;
    }

    //delete Products from Cart
    public int deleteFromCart(int aid, String pid) {
        String sql = "delete from Cart where aid=? and pid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            st.setString(2, pid);
            return st.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public int deleteAfterPayment(int aid) {
        String sql = "delete from Cart where aid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            return st.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    //get tid
    public int getTid() {
        String sql = "	select TOP 1(Tid) from ProductTransaction order by Tid desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("Tid");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //add Transaction
    public int addTransaction(int aid, int money) {
        String sql = "insert into ProductTransaction values(?,?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, money);
            st.setInt(2, aid);
            return st.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    public Date getDate() {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        return date;
    }

    //add Transactiondetail
    public int addTransactionDetail(String pid, int number) {
        String sql = "insert into TransactionDetail values (?,?,?,?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, getTid());
            st.setString(2, pid);
            st.setInt(3, number);
            st.setDate(4, getDate());
            return st.executeUpdate();

        } catch (Exception e) {
        }
        return 0;
    }

    //get Product sale
    public int getProductSale(String pid) {
        String sql = "select number from ProductSale where pid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("number");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //add Product sale
    public int addProductSale(int number, String pid) {
        String sql = "insert into ProductSale values(?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid);
            st.setInt(2, number);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;

    }

    //update Product sale
    public int updateProductSale(int number, String pid) {
        String sql = "update ProductSale set number=? where pid=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, number);
            st.setString(2, pid);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;

    }

    //get product sale according to account
    public int getSpendingMoneyFromAccount(int aid) {
        String sql ="select a.aid as aid ,sum(money) as money from(\n" +
"select sum(money) as money,pt.aid as aid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid where pt.aid=?   group by pt.Tid,pt.aid\n" +
")\n" +
"as a group by a.aid order by sum(money) desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("money");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //get ALL transaction
    public List<Transaction> getAllTransaction() {
        DAOCart dc = new DAOCart();
        String sql = "select pt.Tid,pt.money,pt.aid,td.date from ProductTransaction  pt join TransactionDetail td "
                + "on pt.tid=td.tid group by pt.Tid,pt.money,pt.aid,td.date order by pt.Tid desc ";
        List<Transaction> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Transaction a = new Transaction(rs.getInt("Tid"), rs.getInt("money"), rs.getInt("aid"), rs.getDate("date"));
                list.add(a);
            }

        } catch (SQLException e) {
        }
        return list;
    }
    //get ALL transaction
    public Transaction getTransactionbyTid(int id) {
        DAOCart dc = new DAOCart();
        String sql = "select * from ProductTransaction  where Tid =? ";
        List<Transaction> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Transaction a = new Transaction(rs.getInt("Tid"), rs.getInt("money"), rs.getInt("aid"));
                return a;
            }

        } catch (SQLException e) {
        }
        return null;
    }

    //get ALL transaction with date
    public List<Transaction> getAllTransactionByDate(Date from, Date to) {
        DAOCart dc = new DAOCart();
        String sql = "select pt.Tid,pt.money,pt.aid,td.date from ProductTransaction  pt join TransactionDetail td "
                + "on pt.tid=td.tid where td.date>=? and td.date<=? group by pt.Tid,pt.money,pt.aid,td.date order by pt.Tid desc ";
        List<Transaction> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, from);
            st.setDate(2, to);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Transaction a = new Transaction(rs.getInt("Tid"), rs.getInt("money"), rs.getInt("aid"), rs.getDate("date"));
                list.add(a);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    //get Product in each transaction
    public List<Product> getProductInTransaction(int Tid) {
        DAO d = new DAO();
        String sql = "select * from products p join TransactionDetail td on p.ID=td.pid where tid=?";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, Tid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setDate(rs.getDate("date"));
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = d.getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setSale(rs.getInt("number"));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    //get Product in each account
    public List<Product> getProductInAccount(int aid) {
        DAO d = new DAO();
        String sql = "select pt.Tid, p.ID,p.name,p.price,p.describe,p.image,p.cid,p.details,td.number,td.date from products p join TransactionDetail "
                + "td on p.ID=td.pid inner join ProductTransaction pt on pt.Tid=td.Tid where pt.aid=? order by pt.Tid desc";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, aid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setDate(rs.getDate("date"));
                p.setTid(rs.getInt("Tid"));
                p.setId(rs.getString("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescribe(rs.getString("describe"));
                p.setImage(rs.getString("image"));
                Category c = d.getCategoryById(rs.getInt("cid"));
                p.setCategory(c);
                p.setSale(rs.getInt("number"));
                p.setDetails(rs.getString("details"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    //Comment
    public List<Rating> getAllRating(String pid) {
        String sql = "select r.aid,r.date,r.comment,r.rating,r.rid from Ratings r where pid=? order by r.rid desc";
        DAO d = new DAO();
        List<Rating> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Rating p = new Rating();
                Account a = d.getAccountFromAid(rs.getInt("aid"));
                p.setDate(rs.getDate("date"));
                p.setA(a);
                p.setComment(rs.getString("comment"));
                p.setRating(rs.getInt("rating"));
                list.add(p);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    //get rating of a pid
    public int getRating(String pid) {
        List<Rating> list = new ArrayList<>();
        list = getAllRating(pid);
        if (list == null || list.size() <= 0) {
            return 0;
        } else {
            int rate = 0;
            for (Rating c : list) {
                rate += c.getRating();
            }
            rate = rate / list.size();
            return rate;
        }
    }

    //Add Comment to Ratings table
    public int addInfomationToRatingsTable(int aid, String pid, int rating,String comment) {
        String sql = "insert into Ratings values(?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid);
            st.setInt(2, aid);
            st.setInt(3, rating);
            st.setDate(4, getDate());
            st.setString(5,comment);
            return st.executeUpdate();
        } catch (Exception e) {
        }
        return 0;

    }

    //get Ratings ID to add Comments table
    public int getRatingsId() {
        String sql = "select top 1(rid) from Ratings order by rid desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("rid");
            }

        } catch (Exception e) {
        }
        return 0;
    }

    
    //get Sale Year
    public int getSaleYear(int year, int monthFrom, int monthTo) {
        String sql = "select sum(number) as sale,count(*) as numberOfTid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid "
                + "where year(td.date)=? and month(td.date)>=? and month(td.date)<=?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            st.setInt(2, monthFrom);
            st.setInt(3, monthTo);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //get sale month
    public int getSaleMonth(int year, int month, int dayFrom, int dayTo) {
        String sql = "select sum(number) as sale,count(*) as numberOfTid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid "
                + "where year(td.date)=? and month(td.date)=? and day(td.date)>=? and day(td.date)<=? ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            st.setInt(2, month);
            st.setInt(3, dayFrom);
            st.setInt(4, dayTo);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //get sale chart of total
    public List<Chart> getSaleChart(int year, int month) {
        List<Chart> list = new ArrayList<>();
        if (year == 0) {
            for (int i = 2021; i <= 2023; i++) {
                Chart a = new Chart();
                a.setMonth("Year " + i);
                a.setY(getSaleYear(i, 0, 12));
                list.add(a);
            }
        } else if (month == 0 && year != 0) {
            Chart a = new Chart();
            a.setMonth("January");
            a.setY(0);
            list.add(a);
            for (int i = 4; i <= 12; i += 4) {
                list.add(new Chart(i, getSaleYear(year, i - 4, i)));
            }
            for (Chart c : list) {
                switch (c.getX()) {
                    case 12:
                        c.setMonth("December");
                        break;
                    case 4:
                        c.setMonth("April");
                        break;
                    case 8:
                        c.setMonth("August");
                        break;

                    default:
                        break;
                }
            }
        } else {
            for (int i = 1; i <= 4; i++) {
                Chart a = new Chart(i, getSaleMonth(year, month, i * 7 - 7, i * 7));
                a.setMonth("Week " + i);
                list.add(a);
            }
        }
        return list;
    }

    //get sale chart of each brand
    public List<Chart> getSaleChartEachBrand(int year, int month) {
        DAO d = new DAO();
        List<Category> cList = d.getAll();
        List<Chart> list = new ArrayList<>();
        if (year != 0) {
            for (int i = 0; i < cList.size(); i++) {
                Chart a = new Chart();
                a.setBrand(cList.get(i));
                a.setY(getSaleEachBrand(year, month, cList.get(i).getId()));
                list.add(a);
            }
        } else {
            for (int i = 0; i < cList.size(); i++) {
                Chart a = new Chart();
                a.setBrand(cList.get(i));
                a.setY(getSaleEachBrandAll(cList.get(i).getId()));
                list.add(a);
            }
        }
        return list;
    }

    //get Sale
    public int getSaleEachBrand(int year, int month, int cid) {
        String sql = "select sum(number) as sale,p.cid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid join products p on td.pid=p.id "
                + "where year(td.date)=? and month(td.date)>=? and month(td.date)<=? and p.cid=? group by p.cid ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            st.setInt(4, cid);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    //get Sale each Brand all
    public int getSaleEachBrandAll(int cid) {
        String sql = "select sum(number) as sale,p.cid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid join products p on td.pid=p.id where  p.cid=?  group by p.cid ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cid);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Chart> getTopChartProduct(int year, int month) {
        DAO d = new DAO();
        String sql = "select sum(number) as sale,p.id,p.name from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid join products p on td.pid=p.id where year(td.date)=? and month(td.date)>=? and month(td.date)<=? group by p.ID,p.name order by sale desc";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chart a = new Chart();
                Product p = d.getProductsBYid((rs.getInt("id")));
                a.setP(p);
                a.setY(rs.getInt("sale"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Chart> getTopChartProduct() {
        DAO d = new DAO();
        String sql = "select sum(number) as sale,p.id,p.name from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid join products p on td.pid=p.id group by p.ID,p.name order by sale desc";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chart a = new Chart();
                Product p = d.getProductsBYid((rs.getInt("id")));
                a.setP(p);
                a.setY(rs.getInt("sale"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public int getTotalSaleChart(int year, int month) {
        String sql = "select sum(money) as sale from ProductTransaction pt inner join TransactionDetail td on pt.Tid=td.Tid "
                + "where year(td.date)=? and month(td.date)>=? and month(td.date)<=?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalSaleChart() {
        String sql = "select sum(money) as sale from ProductTransaction pt inner join TransactionDetail td on pt.Tid=td.Tid ";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("sale");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Account> getAccountUserChart(int year, int month) {
        DAO d = new DAO();
        List<Account> list = new ArrayList<>();
        String sql = "select a.aid as aid ,sum(money) as money from(\n" +
"select sum(money) as money,pt.aid as aid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid where  year(td.date)=? and month(td.date)>=? and month(td.date)<=?   group by pt.Tid,pt.aid\n" +
")\n" +
"as a group by a.aid order by sum(money) desc";
               
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account a = d.getAccountFromAid(rs.getInt("aid"));
                a.setMoney(rs.getInt("money"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Account> getAccountUserChart() {
        DAO d = new DAO();
        List<Account> list = new ArrayList<>();
        String sql = "select a.aid as aid ,sum(money) as money from(\n" +
"select sum(money) as money,pt.aid as aid from ProductTransaction pt join TransactionDetail td on pt.Tid=td.Tid   group by pt.Tid,pt.aid\n" +
")\n" +
"as a group by a.aid order by sum(money) desc";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account a = d.getAccountFromAid(rs.getInt("aid"));
                a.setMoney(rs.getInt("money"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Chart> getRatingChart(int year, int month) {
        DAO d = new DAO();
        String sql = "select count(*) as number,rating from Ratings  where year(date)=? and month(date)>=? and month(date)<=? group by rating";
        List<Chart> list = new ArrayList<>();
        list.add(new Chart(1, 0));
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chart a = new Chart();
                a.setX(rs.getInt("rating"));
                a.setY(rs.getInt("number"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Chart> getRatingChart() {
        DAO d = new DAO();
        String sql = "select count(*) as number,rating from Ratings group by rating";
        List<Chart> list = new ArrayList<>();
        list.add(new Chart(1, 0));
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Chart a = new Chart();
                a.setX(rs.getInt("rating"));
                a.setY(rs.getInt("number"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }

    //get gender
    public List<Chart> getGenderChart() {
        DAO d = new DAO();
        String sql = "select count(*) as number from(\n"
                + "select count(pt.aid) as aid from ProductTransaction pt  join AccountInfo ai on pt.aid=ai.aid where gender=? group by pt.aid,ai.gender) as a";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, true);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("Male");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            
            st.setBoolean(1, false);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("FeMale");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            
        } catch (Exception e) {
        }
        return list;
    }
    
    //get gender
    public List<Chart> getGenderChart(int year,int month) {
        DAO d = new DAO();
        String sql = "select count(*) as number from(\n" +
"select count(pt.aid) as aid from ProductTransaction pt  join AccountInfo ai on pt.aid=ai.aid  inner join TransactionDetail td on pt.Tid=td.Tid "
                + "where year(td.date)=? and month(td.date)>=? and month(td.date)<=? and gender=? group by pt.aid,ai.gender) as a";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, year);
            if (month == 0) {
                st.setInt(2, 0);
                st.setInt(3, 12);
            } else {
                st.setInt(2, month);
                st.setInt(3, month);
            }
            st.setBoolean(4, true);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("Male");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            
            st.setBoolean(4, false);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("FeMale");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            
        } catch (Exception e) {
        }
        return list;
    }
    //get age
    public List<Chart> getAgeChart() {
        DAO d = new DAO();
        String sql = "select count(*) as number from(select pt.aid,ai.age from ProductTransaction pt  join AccountInfo ai on pt.aid=ai.aid  inner join TransactionDetail td on pt.Tid=td.Tid "
                + "where age>=? and age<=? group by pt.aid,ai.age) as a";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, 10); st.setInt(2, 25);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("10-25");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 25); st.setInt(2, 30);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("25-30");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 30); st.setInt(2, 40);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("30-40");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 40); st.setInt(2, 60);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth(">40");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }
    //get age
    public List<Chart> getAgeChart(int year,int month) {
        DAO d = new DAO();
        String sql = "select count(*) as number from(select pt.aid,ai.age from ProductTransaction pt  join AccountInfo ai on pt.aid=ai.aid  inner join TransactionDetail td on pt.Tid=td.Tid "
                + "where age>=? and age<=? and year(td.date)=? and month(td.date)>=? and month(td.date)<=? group by pt.aid,ai.age) as a";
        List<Chart> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(3, year);
            if (month == 0) {
                st.setInt(4, 0);
                st.setInt(5, 12);
            } else {
                st.setInt(4, month);
                st.setInt(5, month);
            }
            st.setInt(1, 10); st.setInt(2, 25);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("10-25");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 25); st.setInt(2, 30);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("25-30");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 30); st.setInt(2, 40);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth("30-40");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
            st.setInt(1, 40); st.setInt(2, 60);
            rs = st.executeQuery();
            if (rs.next()) {
                Chart a = new Chart();
                a.setMonth(">40");
                a.setY(rs.getInt("number"));
                list.add(a);
            }
        } catch (Exception e) {
        }
        return list;
    }
    public int deleteFromProduct(){
        String sql="delete  from ProductTransaction where Tid>154 and Tid%2=1";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            return st.executeUpdate();
        }
        catch(SQLException e){
                
            }
        return 0;
    }

}
