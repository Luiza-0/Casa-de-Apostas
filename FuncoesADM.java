import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


//CLASSE DO "PERFIL" ADMINISTRADOR
public class FuncoesADM {
	private String url="jdbc:postgresql://localhost:5432/casaapostas";
	private String usuario="postgres";
	private String senha="1234";

	Scanner sc = new Scanner (System.in);
	Connection con = null;

	//FUNÇÃO PARA O SORTEIO DE NÚMERO PARA CÓDIGOS
	public Integer sortearCodigo() {
		int numeroMinimo = 100001;
		int numeroMaximo = 199999;

		// Gera um número aleatório entre numeroMinimo e numeroMaximo
		int numeroSorteado = (int) (Math.random() * (numeroMaximo - numeroMinimo + 1)) + numeroMinimo;

		return numeroSorteado;
	}

	//FUNÇÃO PARA A CONEXÃO COM O BANCO DE DADOS 
	public Connection conexaoBanco(){	
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, usuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//-----------------------------------------------
	
	//FUNÇÕES CRUD 
		//   1. CADASTRAR
		//   2. LISTAR
		//   3. MODIFICAR
		//   4. DELETAR
		
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA CADASTRAR MODALIDADES
	public void CadastrarModalidades(Connection con){

		int cod_modalidade = sortearCodigo(), qtd_jogos;
		String nome_modalidade, desc_modalidade;
		Statement statement;

		System.out.println("CADASTRO DE MODALIDADES");
		System.out.println();
		System.out.printf("CODIGO DA MODALIDADE: " + cod_modalidade);
		System.out.println();
		System.out.printf("QUANTIDADE DE JOGOS: ");
		qtd_jogos = sc.nextInt();
		System.out.println();
		System.out.printf("MODALIDADE:");
		nome_modalidade = sc.next();
		System.out.println();
		System.out.printf("DESCRICAO MODALIDADE:");
		desc_modalidade = sc.next();
		System.out.println();
		try {
			String query=String.format("INSERT INTO %s(cod_modalidade, nome_modalidade,desc_modalidade,qtd_jogos) VALUES('%s', '%s', '%s','%s');", "MODALIDADE", cod_modalidade, nome_modalidade,desc_modalidade,qtd_jogos);
			statement=con.createStatement();
			statement.executeUpdate(query);
			System.out.println("Modalidade Cadastrada com Sucesso!");
			
			System.out.println();
			ListarModalidades(con);
			System.out.println();
		}catch (Exception e){
			System.out.println(e);
		}

	}

	//FUNÇÃO PARA CADASTRAR JOGOS
	public void CadastrarJogos(Connection con){
			int cod_jogo = sortearCodigo();
			Statement statement;

			ListarModalidades(con);
			System.out.println();

			String time_01, time_02;
			System.out.println("CADASTRO DE JOGO");
			System.out.println();

			System.out.println("CODIGO: " + cod_jogo);
			System.out.println();
			System.out.printf("CODIGO MODALIDADE:");
			int cod_modalidade = sc.nextInt();
			System.out.println();
			System.out.printf("TIME 1: ");
			time_01 = sc.next();
			System.out.println();
			System.out.printf("TIME 2:");
			time_02 = sc.next();
			System.out.println();
			try {
				String query=String.format("INSERT INTO %s(cod_jogo, cod_modalidade,time_01,time_02) VALUES('%s','%s','%s','%s');", "JOGO",cod_jogo,cod_modalidade,time_01,time_02);
				statement=con.createStatement();
				statement.executeUpdate(query);
				System.out.println("Jogo Cadastrado com Sucesso!");
				
				System.out.println();
				ListarJogos(con);
				System.out.println();
			}catch (Exception e){
				System.out.println(e);
			}
		}

	//FUNÇÃO PARA CADASTRAR RESULTADOS
	public void CadastrarResultados(Connection con){

		Float valor_pago_p_jogador;
		int cod_resultado = sortearCodigo(),cod_modalidade, cod_jogo ;
		String time_vencedor;
		Statement statement;

		System.out.println();
		ListarJogos(con);
		System.out.println();
		ListarModalidades(con);
		System.out.println();


		System.out.println("CADASTRO DE RESULTADOS");

		System.out.println("CÓDIGO DO RESULTADO: " + cod_resultado);
		System.out.println();
		System.out.printf("MODALIDADE Nº: ");
		cod_modalidade = sc.nextInt();
		System.out.println();
		System.out.printf("JOGO Nº:");
		cod_jogo = sc.nextInt();
		System.out.println();
		System.out.printf("VALOR PAGO PARA O JOGADOR:");
		valor_pago_p_jogador = sc.nextFloat();
		System.out.println();
		System.out.printf("TIME VENCEDOR:");
		time_vencedor = sc.next();
		System.out.println();

		try {
			String query=String.format("INSERT INTO %s(cod_resultado, cod_modalidade, cod_jogo,valor_pago_p_jogador, time_vencedor) VALUES('%s', '%s', '%s', '%s','%s');", "RESULTADO", cod_resultado,cod_modalidade, cod_jogo,valor_pago_p_jogador, time_vencedor);
			statement=con.createStatement();
			statement.executeUpdate(query);
			System.out.println("Resultado Cadastrado com Sucesso!");
			
			System.out.println();
			ListarResultados(con);
			System.out.println();
			
		}catch (Exception e){
			System.out.println(e);
		}

	}

	
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA LISTAR MODALIDADES
	public void ListarModalidades(Connection conn){
			Statement statement;
			ResultSet rs=null;
			System.out.printf("LISTA DE MODALIDADES");
			try {
				String query=String.format("SELECT * FROM MODALIDADE");
				statement=conn.createStatement();
				rs=statement.executeQuery(query);
				while (rs.next()){
					System.out.println();
					System.out.print("Código: " + rs.getString("cod_modalidade")+" ");
					System.out.print("| " + rs.getString("nome_modalidade")+ " ");
				}
			}catch (Exception e){
				System.out.println(e);
			}
			System.out.println();
			System.out.println();
		}

	//FUNÇÃO PARA LISTAR JOGOS
	public void ListarJogos(Connection conn){
		Statement statement;
		ResultSet rs=null;
		System.out.printf("LISTA DE JOGOS");
		try {
			String query=String.format("SELECT JOGO.*, MODALIDADE.nome_modalidade FROM JOGO INNER JOIN MODALIDADE ON JOGO.cod_modalidade = MODALIDADE.cod_modalidade");
			statement=conn.createStatement();
			rs=statement.executeQuery(query);
			while (rs.next()){
				System.out.println();
				System.out.print("Código: " +rs.getString("cod_jogo")+" ");
				System.out.print(" | " + rs.getString("time_01")+" x ");
				System.out.print(rs.getString("time_02")+ " ");
				System.out.print(" | " + rs.getString("nome_modalidade")+"  ");
			}
		}catch (Exception e){
			System.out.println(e);
		}
		System.out.println();
		System.out.println();
	}

	//FUNÇÃO PARA LISTAR RESULTADOS
	public void ListarResultados(Connection conn){

		Statement statement;
		ResultSet rs=null;
		System.out.printf("LISTA DE RESULTADOS");
		try {
			String query=String.format("SELECT RESULTADO.*, JOGO.time_01, JOGO.time_02 FROM RESULTADO INNER JOIN jogo ON resultado.cod_jogo = jogo.cod_jogo");
			statement=conn.createStatement();
			rs=statement.executeQuery(query);
			while (rs.next()){
				System.out.println();
				System.out.print("(" + rs.getString("cod_resultado") + ") - ");
				System.out.print("Jogo: " + rs.getString("time_01")+" x ");
				System.out.print(rs.getString("time_02")+ " | ");
				System.out.print("Vencedor: " + rs.getString("time_vencedor"));
				System.out.print("| Valor: R$ " + rs.getString("valor_pago_p_jogador"));
			}
		}catch (Exception e){
			System.out.println(e);
		}
		System.out.println();
		System.out.println();
	}

	
	//-----------------------------------------------
	
	
	//FUNÇÃO PARA MODIFICAR RESULTADOS
	public void ModificarResultados(Connection conn){
			Statement statement;
			
			System.out.println();
			ListarResultados(conn);
			System.out.println();
			
			System.out.println();
			System.out.println("Modifique o time vencedor!");
			System.out.println();
			
			System.out.printf("RESULTADO:" );
			int cod_resultado = sc.nextInt();
			System.out.println();

			System.out.printf("NOVO TIME VENCEDOR:" );
			String novotime_vencedor = sc.next();
			System.out.println();

			System.out.printf("ANTIGO TIME VENCEDOR:" );
			String antigotime_vencedor = sc.next();
			System.out.println();
			
			try {
				String query=String.format("UPDATE RESULTADO SET time_vencedor='%s' WHERE time_vencedor='%s' AND cod_resultado = '%s'", novotime_vencedor, antigotime_vencedor, cod_resultado);
				statement=conn.createStatement();
				statement.executeUpdate(query);
				System.out.println();
				System.out.println();
				System.out.println("Aposta modificada com sucesso");
				ListarResultados(conn);
				System.out.println();
			}catch (Exception e){
				System.out.println(e);
			}
		}
	
	
	//-----------------------------------------------
	
	//FUNÇÃO PARA DELETAR MODALIDADES
	public void DeletarModalidades(Connection conn){
		Statement statement;
		
		System.out.println();
		ListarModalidades(conn);
		System.out.println();
		
		System.out.println("CÓDIGO DO MODALIDADE: ");
		Integer cod_modalidade = sc.nextInt();
		
		try{
			String query=String.format("DELETE FROM MODALIDADE WHERE cod_modalidade = '%s'",cod_modalidade);
			statement=conn.createStatement();
			statement.executeUpdate(query);
			System.out.println("Modalidade deletada com sucesso!");
			
			System.out.println();
			ListarModalidades(conn);
			System.out.println();
			
		}catch (Exception e){
			System.out.println("");
		}
	}
	
	//FUNÇÃO PARA DELETAR JOGOS
	public void DeletarJogos(Connection conn){
		Statement statement;
		
		System.out.println();
		ListarJogos(conn);
		System.out.println();
		
		System.out.println("CÓDIGO DO JOGO: ");
		Integer cod_jogo = sc.nextInt();
		
		try{
			String query=String.format("DELETE FROM JOGO WHERE cod_jogo = '%s'",cod_jogo);
			statement=conn.createStatement();
			statement.executeUpdate(query);
			System.out.println();
			System.out.println();
			System.out.println("Jogo deletado com sucesso!");
			
			ListarJogos(conn);
			System.out.println();
			
		}catch (Exception e){
			System.out.println("");
		}
	}

	//FUNÇÃO PARA DELETAR RESULTADOS
	public void DeletarResultados(Connection conn){
		Statement statement;
		
		System.out.println();
		ListarResultados(conn);
		System.out.println();
		
		System.out.println("CÓDIGO DO RESULTADO: ");
		Integer cod_resultado = sc.nextInt();
		
		try{
			String query=String.format("DELETE FROM RESULTADO WHERE cod_resultado = '%s'",cod_resultado);
			statement=conn.createStatement();
			statement.executeUpdate(query);
			System.out.println();
			System.out.println();
			System.out.println("Resultado deletado com sucesso!");
			
			ListarResultados(conn);
			System.out.println();
			
		}catch (Exception e){
			System.out.println("");
		}
	}

	//FUNÇÃO PARA DELETAR APOSTADORES
	public void DeletarApostadores(Connection conn){
		FuncoesApostadores apo = new FuncoesApostadores();
			Statement statement;
			
			System.out.println();
			apo.ListarApostadores(conn);
			System.out.println();
			
			System.out.println("CÓDIGO DO APOSTADOR: ");
			Integer cod_apostador = sc.nextInt();
			
			try{
				String query=String.format("DELETE FROM APOSTADOR WHERE cod_apostador = '%s'",cod_apostador);
				statement=conn.createStatement();
				statement.executeUpdate(query);
				System.out.println();
				System.out.println();
				System.out.println("Apostador deletado com sucesso!");
				
				System.out.println();
				apo.ListarApostadores(conn);
				
			}catch (Exception e){
				System.out.println("");
			}
		}
		
}

