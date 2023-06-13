import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

//CLASSE DO "PERFIL" APOSTADOR
public class FuncoesApostadores {

	FuncoesADM func = new FuncoesADM();
	Connection con = null;
	Scanner sc = new Scanner(System.in);

	
	//-----------------------------------------------
	
	//FUNÇÕES CRUD 
		//   1. CADASTRAR
		//   2. PESQUISAR | LISTAR
		//   3. MODIFICAR
		//   4. DELETAR
		
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA CADASTRAR APOSTADORES
	public void CadastrarApostadores(Connection con){
		Statement statement;
		Integer cod_apostador = func.sortearCodigo();
		String nome_apostador, cpf_apostador, email_apostador, tel_apostador;

		System.out.println("CADASTRO DE APOSTADORES");

		System.out.println("CODIGO: " + cod_apostador);
		System.out.println();
		System.out.printf("NOME: ");
		nome_apostador = sc.nextLine();
		System.out.println();
		System.out.printf("CPF:");
		cpf_apostador = sc.nextLine();
		System.out.println();
		System.out.printf("EMAIL:");
		email_apostador = sc.nextLine();
		System.out.println();
		System.out.printf("TELEFONE:");
		tel_apostador = sc.nextLine();


		try {
			String query=String.format("INSERT INTO %s(cod_apostador, nome_apostador,cpf_apostador, email_apostador, tel_apostador) VALUES('%s','%s','%s','%s', '%s')", "APOSTADOR",cod_apostador,nome_apostador,cpf_apostador,email_apostador,tel_apostador);
			statement=con.createStatement();
			statement.executeUpdate(query);
			System.out.println();
			System.out.println();
			System.out.println("Apostador Cadastrado com Sucesso!");
			System.out.println();
			
			ListarApostadores(con);
			System.out.println();
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	//FUNÇÃO PARA CADASTRAR APOSTA
	public void CadastrarAposta(Connection con){
		Integer cod_aposta= func.sortearCodigo(), cod_apostador, cod_modalidade, cod_jogo; 
		String  dt_aposta, time_vencedor;
		Statement statement;
		
		System.out.println();
		ListarApostadores(con);
		System.out.println();
		func.ListarModalidades(con);
		System.out.println();
		func.ListarJogos(con);
		System.out.println();
		
		System.out.println("APOSTA Nº:" + cod_aposta);
		System.out.println();
		System.out.printf("APOSTADOR Nº: ");
		cod_apostador = sc.nextInt();
		System.out.println();
		System.out.printf("MODALIDADE Nº:");
		cod_modalidade = sc.nextInt();
		System.out.println();
		System.out.printf("JOGO Nº:");
		cod_jogo = sc.nextInt();
		System.out.println();
		System.out.printf("DATA DA APOSTA:");
		dt_aposta = sc.next();
		System.out.println();
		System.out.printf("APOSTA (TIME VENCEDOR):");
		time_vencedor = sc.next();
		System.out.println();
		
		try {
			String query=String.format("INSERT INTO %s(cod_aposta,cod_apostador, cod_modalidade, cod_jogo, dt_aposta, time_vencedor) VALUES('%s','%s','%s','%s','%s','%s');", "APOSTA",cod_aposta,cod_apostador,cod_modalidade,cod_jogo,dt_aposta,time_vencedor);
			statement=con.createStatement();
			statement.executeUpdate(query);
			
			System.out.println();
			System.out.println();
			System.out.println("Aposta Realizada com Sucesso!");
			System.out.println();
			
			ListarApostas(con);
			System.out.println();
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA MODIFICAR APOSTA
	public void ModificarAposta(Connection conn){
		Statement statement;
		
		System.out.println();
		ListarApostadores(conn);
		System.out.println();
		
		System.out.println();
		ListarApostas(conn);
		System.out.println();
		
		System.out.println();
		System.out.println("Modifique o time vencedor!");
		System.out.println();
		
		System.out.printf("APOSTADOR:" );
		int cod_apostador = sc.nextInt();
		System.out.println();
		
		System.out.printf("APOSTA:" );
		int cod_aposta = sc.nextInt();
		System.out.println();
		

		System.out.printf("NOVO TIME VENCEDOR:" );
		String novotime_vencedor = sc.next();
		System.out.println();

		System.out.printf("ANTIGO TIME VENCEDOR:" );
		String antigotime_vencedor = sc.next();
		System.out.println();
		
		try {
			String query=String.format("UPDATE APOSTA SET time_vencedor='%s' WHERE time_vencedor='%s' AND cod_aposta IN (SELECT cod_aposta FROM APOSTA INNER JOIN APOSTADOR ON APOSTA.cod_apostador = APOSTADOR.cod_apostador INNER JOIN JOGO ON APOSTA.cod_jogo = JOGO.cod_jogo WHERE APOSTADOR.cod_apostador = '%s' AND APOSTA.cod_aposta = '%s')",novotime_vencedor,antigotime_vencedor, cod_apostador, cod_aposta);
			statement=conn.createStatement();
			statement.executeUpdate(query);
			System.out.println();
			System.out.println();
			System.out.println("Aposta modificada com sucesso!");
			System.out.println();
			
			ListarApostas(conn);
			System.out.println();
		}catch (Exception e){
			System.out.println(e);
		}
	}

	
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA PESQUISAR POR APOSTADOR
	public void PesquisarPorApostador(Connection conn){
		Statement statement;
		ResultSet rs = null;
		
		System.out.printf("Digite o nome do apostador: ");
		String nome = sc.next();
		System.out.println();
		try {
			String query = String.format("SELECT APOSTA.*, APOSTADOR.nome_apostador, JOGO.time_01, JOGO.time_02\r\n"
					+ "FROM APOSTADOR\r\n"
					+ "INNER JOIN APOSTA ON APOSTADOR.cod_apostador = APOSTA.cod_apostador\r\n"
					+ "INNER JOIN JOGO ON JOGO.cod_jogo = APOSTA.cod_jogo\r\n"
					+ "WHERE APOSTADOR.nome_apostador = '%s'\r\n"
					 , nome);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.println();
				System.out.print(rs.getString("dt_aposta"));
				System.out.print(" " + rs.getString("nome_apostador"));
				System.out.print(" | " + rs.getString("time_01"));
				System.out.print(" x " + rs.getString("time_02"));
				System.out.print(" | " + rs.getString("time_vencedor"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println();
		System.out.println();
	}
	
	//FUNÇÃO PARA PESQUISAR POR DATA
	public void PesquisarPorData(Connection conn) {

		Statement statement;
		ResultSet rs = null;
		
		System.out.println("Digite a data da aposta: ");
		String data = sc.next();		
		try {
			String query = String.format("SELECT APOSTA.*, APOSTADOR.nome_apostador, JOGO.time_01, JOGO.time_02\r\n"
					+ "FROM APOSTADOR\r\n"
					+ "INNER JOIN APOSTA ON APOSTADOR.cod_apostador = APOSTA.cod_apostador\r\n"
					+ "INNER JOIN JOGO ON JOGO.cod_jogo = APOSTA.cod_jogo\r\n"
					+ "WHERE APOSTA.dt_aposta = '%s'\r\n"
					 , data);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.println();
				System.out.print(rs.getString("dt_aposta"));
				System.out.print(" " + rs.getString("nome_apostador"));
				System.out.print(" | " + rs.getString("time_01"));
				System.out.print(" x " + rs.getString("time_02"));
				System.out.print(" | " + rs.getString("time_vencedor"));
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println();
		System.out.println();
	}

	

	//FUNÇÃO PARA LISTAR APOSTADORES
	public void ListarApostadores(Connection conn){
		Statement statement;
		ResultSet rs=null;
		System.out.printf("LISTA DE APOSTADORES");
		try {
			String query=String.format("SELECT * FROM APOSTADOR");
			statement=conn.createStatement();
			rs=statement.executeQuery(query);
			while (rs.next()){
				System.out.println();
				System.out.print("(" + rs.getString("cod_apostador")+") ");
				System.out.print( "- " +rs.getString("nome_apostador")+ "");
				System.out.print(" | CPF: " + rs.getString("cpf_apostador"));
				System.out.print(" | Email: " + rs.getString("email_apostador"));
				System.out.print(" | Telefone: " + rs.getString("tel_apostador"));
			}
		}catch (Exception e){
			System.out.println(e);
		}
		System.out.println();
		System.out.println();
	}

	//FUNÇÃO PARA LISTAR APOSTAS
	public void ListarApostas(Connection conn){
		Statement statement;
		ResultSet rs = null;
		
		System.out.printf("LISTA DE APOSTAS");

		try {
			String query = String.format("SELECT APOSTA.*, APOSTADOR.nome_apostador, JOGO.time_01, JOGO.time_02\r\n"
												+ "FROM APOSTADOR\r\n"
														+ "INNER JOIN APOSTA ON APOSTADOR.cod_apostador = APOSTA.cod_apostador\r\n"
																+ "INNER JOIN JOGO ON JOGO.cod_jogo = APOSTA.cod_jogo");
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				System.out.println();
				System.out.print("(" + rs.getString("cod_aposta") + ") ");
				System.out.print("- " + rs.getString("dt_aposta") + "");
				System.out.print(" " + rs.getString("nome_apostador"));
				System.out.print(" | " + rs.getString("time_01"));
				System.out.print(" x " + rs.getString("time_02"));
				System.out.print(" | " + rs.getString("time_vencedor"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println();
		System.out.println();

	}


	//-----------------------------------------------

	
	//FUNÇÃO PARA DELETAR APOSTAS
	public void DeletarAposta(Connection conn){
		Statement statement;
		
		System.out.println();
		ListarApostas(conn);
		System.out.println();
		
		System.out.println("CÓDIGO DA APOSTA: ");
		Integer cod_aposta = sc.nextInt();
		
		try{
			String query=String.format("DELETE FROM APOSTA WHERE cod_aposta = '%s'",cod_aposta);
			statement=conn.createStatement();
			statement.executeUpdate(query);
			System.out.println();
			System.out.println();
			System.out.println("Aposta deletada com sucesso!");
			System.out.println();
			
			ListarApostas(conn);
			System.out.println();
			
		}catch (Exception e){
			System.out.println("");
		}
	}


}
