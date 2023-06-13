import java.sql.Connection;
import java.util.Scanner;

//CLASSE PRINCIPAL
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		FuncoesADM ADM = new FuncoesADM();
		FuncoesApostadores APO = new FuncoesApostadores();
		
		Connection t = ADM.conexaoBanco();
		Integer opcao;
		//MENU 
		System.out.println();
		System.out.println();
		System.out.println("SEJA BEM VINDO A CASA DE APOSTAS!!");
		System.out.println("-----------------------------------");
		System.out.println();
		System.out.println();
		System.out.println("MENU:");
		System.out.println();
		
        do {
            System.out.println("Escolha uma das opções abaixo:");
            System.out.println("[1] Entrar como Administrador");
            System.out.println("[2] Entrar como Apostador");
            System.out.println("[3] Sair");

            
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                	System.out.println("Escolha uma das opções abaixo:");
                    System.out.println("[1] Cadastrar Modalidades");
                    System.out.println("[2] Cadastrar Jogos");
                    System.out.println("[3] Cadastrar Resultados");
                    System.out.println("[4] Listar Modalidades");
                    System.out.println("[5] Listar Jogos");
                    System.out.println("[6] Listar Resultados");
                    System.out.println("[7] Modificar Resultados");
                    System.out.println("[8] Deletar Apostador");
                    System.out.println("[9] Deletar Modalidade");
                    System.out.println("[10] Deletar Jogo");
                    System.out.println("[11] Deletar Resultado");

                    
                    Integer opcaoADM = sc.nextInt();
                    
                    switch (opcaoADM) {
                    
	                    case 1:
	                    	ADM.CadastrarModalidades(t);
	                    	break;
	                   
	                    
		                case 2:
		                	ADM.CadastrarJogos(t);
		                	break;
		                
	            		
				        case 3:
				        	ADM.CadastrarResultados(t);
				        	break;
				        
				        case 4:
	                    	ADM.ListarModalidades(t);
	                    	break;
	                   
	                    
		                case 5:
		                	ADM.ListarJogos(t);
		                	break;
		                
	            		
				        case 6:
				        	ADM.ListarResultados(t);
				        	break;
		                
				        case 7:
				        	ADM.ModificarResultados(t);
				        	break;
				        	
				        case 8:
				        	ADM.DeletarApostadores(t);
				        	break;
				        	
				        case 9:
				        	ADM.DeletarModalidades(t);
				        	break;
				        	
				        case 10:
				        	ADM.DeletarJogos(t);
				        	break;
				        	
				        case 11:
				        	ADM.DeletarResultados(t);
				        	break;
				        	
				        
                    }
            

                    break;

                case 2:
                	
                	System.out.println("Escolha uma das opções abaixo:");
                    System.out.println("[1] Se cadastrar como Apostador");
                    System.out.println("[2] Cadastrar Aposta");
                    System.out.println("[3] Modificar Aposta");
                    System.out.println("[4] Pesquisar Aposta por data");
                    System.out.println("[5] Pesquisar Aposta por Apostador");
                    System.out.println("[6] Listar Apostas");
                    System.out.println("[7] Listar Apostadores");
                    System.out.println("[8] Deletar Aposta");
                    
                    Integer opcaoAPO = sc.nextInt();
                    
                    switch (opcaoAPO) {
                    
	                    case 1:
	                    	APO.CadastrarApostadores(t);
	                    	break;
	                   
	                    
		                case 2:
		                	APO.CadastrarAposta(t);
	                    	break;
		                
	            		
				        case 3:
				        	APO.ModificarAposta(t);
	                    	break;
				        
				        case 4:
				        	APO.PesquisarPorData(t);
	                    	break;
	                   
	                    
		                case 5:
		                	APO.PesquisarPorApostador(t);
	                    	break;
		                
	            		
				        case 6:
				        	APO.ListarApostas(t);
				        	break;
				        
				        case 7:
	                    	APO.ListarApostadores(t);
	                    	break;
	                   
	                    
		                case 8:
		                	APO.DeletarAposta(t);
		                	break;
		                	
		                
                    }
               
                default:
            }
        
            

        } while (opcao!=3);
        
        System.out.println("_______________________________________");
        System.out.println("Volte sempre a nossa casa de apostas!!");
        System.out.println("_______________________________________");   
        
        sc.close();
	}
}
	

