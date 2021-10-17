package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {

	public ProductDAO() {
		// TODO Auto-generated constructor stub
	}
public void insertProduct(Product product ) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println(product);
		String sql = "insert into PRODUCT(prod_no ,prod_name,prod_detail,manufacture_day,price,image_file,reg_date) values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		//String sql = "insert into PRODUCT VALUES (SEQ_PRODUCT_PROD_NO.NEXTVAL,?";
		//seq_product_prod_no.nextval로 넣을때 마다 번호를 매겨서 입력
		//sysdate는 sql문으로 현재날짜를 입력할때 사용
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
//		stmt.setDate(6, product.getRegDate());
		stmt.executeUpdate();
		
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from Product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();

		return product;
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		System.out.println(search.getCurrentPage());
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

//		System.out.println("ProductDAO: searchCondition: "+ search.getSearchCondition()+"  searchKeyword: "+search.getSearchKeyword());
//		String sqlcount = "select count(*) from PRODUCT ";
//		PreparedStatement stmtcount = con.prepareStatement(sqlcount);
//		ResultSet rscount = stmtcount.executeQuery();
//		rscount.next();
//		int total =rscount.getInt("count(*)");
//		
//		System.out.println(total);
////		String sql = "select * from PRODUCT ";
		
		
		
		String sql = "select v.* from (select rownum rnum, vr.*,TRAN_STATUS_CODE from PRODUCT vr,transaction t where";
		if (search.getSearchCondition() != null) {
			System.out.println("ProductDAO: searchCondition Not Null"+search.getSearchCondition());
			//검색조건이 들어오면
			if (search.getSearchCondition().equals("0")) {
				//검색조건이 0번째의 검색조건에 해당하는 상품번호 이면
				sql += " vr.PROD_NO like '%" + search.getSearchKeyword()
						+ "%' and";
			} else if (search.getSearchCondition().equals("1")) {
				//검색조건이 1번째의 검색조건에 해당하는 상품이름 이면
				sql += " vr.PROD_NAME like '%" + search.getSearchKeyword()
						+ "%' and";
			} else if (search.getSearchCondition().equals("2")) {
				//검색조건이 2번째의 검색조건에 해당하는 상품가격 이면
				sql += " vr.PRICE like '%" + search.getSearchKeyword()
						+ "%' and";
			}
		}else
			System.out.println("ProductDAO: searchCondition Null");
		
		sql += " vr.prod_no = t.prod_no(+)";
		sql += " ORDER BY vr.PROD_NO) v";
		//sql += " where rnum BETWEEN "+(search.getCurrentPage()*search.getPageSize()-2)+" and "+(search.getCurrentPage()*search.getPageSize());
		System.out.println("ProductDAO: "+ sql);
		
		
		//==> TotalCount GET
				int totalCount = this.getTotalCount(sql);
				System.out.println("Product DAO :: totalCount  :: " + totalCount);
				
				//==> CurrentPage 게시물만 받도록 Query 다시구성
				sql = makeCurrentPageSql(sql, search);
				PreparedStatement pStmt = con.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();

				System.out.println("DAO search :"+search);
				
				List<Product> list = new ArrayList<Product>();
				while(rs.next()) {
				Product vo = new Product();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("PROD_NAME"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setProdDetail(rs.getString("PROD_DETAIL"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				vo.setProTranCode(rs.getString("TRAN_STATUS_CODE"));

				list.add(vo);
				}

				//==> totalCount 정보 저장
				map.put("totalCount", new Integer(totalCount));
				//==> currentPage 의 게시물 정보 갖는 List 저장
				map.put("list", list);
				rs.close();
				pStmt.close();
				con.close();
		System.out.println("ProductDAO: "+list.size()+"     "+map.toString());
		return map;
	}

	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "update PRODUCT set PROD_NAME=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=?,PROD_DETAIL=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getManuDate());
		stmt.setInt(3, product.getPrice());
		stmt.setString(4, product.getFileName());
		stmt.setString(5, product.getProdDetail());
		stmt.setInt(6, product.getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
	}
	private int getTotalCount(String sql) throws Exception {
		System.out.println(">>>>>  "+ sql);
		sql = "SELECT COUNT(*) "+
		          "FROM (" +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("Product DAO :: make SQL :: "+ sql);	
		
		return sql;
	}

}
