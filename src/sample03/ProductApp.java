package sample03;

import java.sql.SQLException;
import java.util.List;

import util.KeyboardReader;

public class ProductApp {
	
	ProductDao dao = new ProductDao();
	KeyboardReader reader = new KeyboardReader();
	
	public void menu() {
		System.out.println("### 상품정보 관리 프로그램 ###");
		System.out.println("------------------------------------------------------------");
		System.out.println("1.전체조회  2.검색  3.상세조회  4.신규등록  5.삭제  0.종료");
		System.out.println("------------------------------------------------------------");
		System.out.println();
		
		System.out.print("### 메뉴 선택: ");
		int menuNo = reader.readInt();
		System.out.println();
		
		try {
			if (menuNo == 1) {
				전체조회();
			} else if (menuNo == 2) {
				검색();
			} else if (menuNo == 3) {
				상세조회();
			} else if (menuNo == 4) {
				신규등록();
			} else if (menuNo == 5) {
				삭제();
			} else if (menuNo == 0) {
				종료();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		menu();
	}
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 상품정보 조회 >>");
		System.out.println("### 전체 상품정보를 확인하세요.");
		
		List<Product> products = dao.getAllProducts();
		
		if (products.isEmpty()) {
			System.out.println("### 상품정보가 존재하지 않습니다.");
			return;
		}
		
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("상품번호\t상품명\t제조사\t가격\t할인율\t할인가\t재고수량\t등록일자");
		System.out.println("-----------------------------------------------------------------------");
		for (Product product : products) {
			System.out.println(product.getNo() + "\t"
					+ product.getName() + "\t"
					+ product.getMaker() + "\t"
					+ product.getPrice() + "\t"
					+ product.getDiscountPct() + "\t"
					+ product.getDiscountedPrice() + "\t"
					+ product.getStock() + "\t"
					+ product.getCreateDate());
		}
	}

	private void 검색() throws SQLException {
		System.out.println("<< 상품 가격 검색 >>");
		System.out.println("### 최소가격 및 최대가격을 입력하여 상품정보를 검색하세요.");
		
		System.out.print("### 최소가격: ");
		int minPrice = reader.readInt();
		System.out.print("### 최대가격: ");
		int maxPrice = reader.readInt();
		if (minPrice > maxPrice) {
			System.out.println("### 최대가격이 최소가격보다 크거나 같아야 합니다.");
			return;
		}
		
		List<Object[]> products = dao.getProductsByPrice(minPrice, maxPrice);

		System.out.println("-----------------------------------------------------------------------");
		System.out.println("상품번호\t상품명\t제조사\t정가\t할인율\t할인가\t재고수량\t등록일자");
		System.out.println("-----------------------------------------------------------------------");
		for (Object[] obj : products) {
			Product product = (Product) obj[0];
			System.out.println(product.getNo() + "\t"
					+ product.getName() + "\t"
					+ product.getMaker() + "\t"
					+ product.getPrice() + "\t"
					+ product.getDiscountPct() + "\t"
					+ obj[1] + "\t"
//					+ product.getDiscountedPrice() + "\t"
					+ product.getStock() + "\t"
					+ product.getCreateDate());
		}
	}

	private void 상세조회() throws SQLException {
		System.out.println("<< 상세 상품정보 조회 >>");
		System.out.println("### 조회할 상품번호를 입력하세요.");
		
		System.out.print("### 상품번호 입력: ");
		int no = reader.readInt();
		
		Product product = dao.getProductByNo(no);
		if (product == null) {
			System.out.println("### [" + no + "]번 상품정보가 존재하지 않습니다.");
		} else {
			System.out.println("--------------------------");
			System.out.println("상품번호 [" + no + "]의 정보");
			System.out.println("--------------------------");
			System.out.println("상품명: " + product.getName());
			System.out.println("제조사: " + product.getMaker());
			System.out.println("정가: " + product.getPrice());
			System.out.println("할인율: " + product.getDiscountPct());
			System.out.println("할인가: " + product.getDiscountedPrice());
			System.out.println("재고수량: " + product.getStock());
			System.out.println("등록일자: " + product.getCreateDate());
			System.out.println("--------------------------");
		}
	}

	private void 신규등록() throws SQLException {
		System.out.println("<< 신규 상품정보 등록 >>");
		System.out.println("### 상품명, 제조사, 가격, 할인율, 수량을 입력하여 상품정보를 등록하세요.");
		
		System.out.print("### 상품명: ");
		String name = reader.readString();
		System.out.print("### 제조사: ");
		String maker = reader.readString();
		System.out.print("### 가격: ");
		int price = reader.readInt();
		System.out.print("### 할인율: ");
		double discountRate = reader.readDouble();
		System.out.print("### 수량: ");
		int stock = reader.readInt();
		
		Product product = new Product(0, name, maker, price, discountRate, stock, null);
		dao.insertProduct(product);
		
		System.out.println("### 신규 상품정보가 저장되었습니다.");
	}

	private void 삭제() throws SQLException {
		System.out.println("<< 상품정보 삭제 >>");
		System.out.println("### 삭제할 상품번호를 입력하세요.");
		
		System.out.print("### 상품번호: ");
		int rowCnt = dao.deleteProduct(reader.readInt());
		System.out.println(rowCnt > 0 ? "### 상품정보가 삭제되었습니다." : "### 상품정보가 삭제되지 않았습니다.");
	}

	private void 종료() {
		System.out.println("<< 프로그램 종료 >>");
		System.out.println("### 프로그램을 종료합니다.");
		System.exit(0);
	}

	public static void main(String[] args) {
		new ProductApp().menu();
	}

}
