import java.util.InputMismatchException;
import java.util.Scanner;

import exception.SistemaException;
import menu.Menu;
import model.Administrador;
import model.Cliente;
import model.Veiculo;
import model.Vendedor;
import service.AdminService;
import service.ClienteService;
import service.VeiculoService;
import service.VendedorService;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Scanner sc = new Scanner (System.in);
		
		ClienteService clienteService = new ClienteService(sc);
		VeiculoService veiculoService = new VeiculoService(sc);
		VendedorService vendedorService = new VendedorService(sc);
		AdminService adminService = new AdminService(sc, veiculoService, vendedorService);
		
		boolean continua = true;
		do {
			try {
			Menu.menu1();
			int opcao1 = sc.nextInt();
			sc.nextLine();
			
			switch (opcao1) {
			case 1:
					Menu.menuCliente1();
					String email = sc.nextLine();
					Cliente cliente = clienteService.confereEmail(email);
					boolean senhaCorreta = false;
					for(int i = 0; i < 3; i++) {
					System.out.println("Agora digite sua senha:");
					String senha = sc.nextLine();
					senhaCorreta = clienteService.conferirSenha(cliente, senha);
						if(!senhaCorreta) {
							System.out.println("Senha incorreta, tente novamente");
						} else {
							break;
						}
					}
						if(!senhaCorreta) {
							break;
						}
						
						Menu.menuCliente2();
						int opcao2 = sc.nextInt();
						
						if(opcao2 == 1) {
							System.out.println("Digite o número referente ao carro desejado: ");
							veiculoService.buscarVeiculosLivres();
							int opcaoCarro = sc.nextInt();
							Veiculo veiculo = veiculoService.alugarVeiculoId(opcaoCarro);
							clienteService.alugarVeiculo(cliente, veiculo);
							
							System.out.println("Digite o número referente ao vendedor que lhe atendeu");
							vendedorService.retornaTodosVendedores();
							int opcaoVendedor = sc.nextInt();
							vendedorService.salvarVeiculo(veiculo, opcaoVendedor);
							
						} else if (opcao2 == 2) {
						System.out.println("Digite o número referente ao carro desejado");
						clienteService.buscarVeiculosAlugados(cliente);
						
						int opcaoCarro = sc.nextInt();
						
						Veiculo veiculoDevolvido = veiculoService.devolverVeiculo(cliente, opcaoCarro);
						clienteService.removerVeiculo(cliente, veiculoDevolvido);
						}
					
					break;
			case 2: 
					Menu.menuCliente1();
					email = sc.nextLine();
					
					Vendedor vendedor = vendedorService.confereEmail(email);
					if(vendedor == null) {
						throw new SistemaException("Vendedor não encontrado!");
					}
					senhaCorreta = false;
					for(int i = 0; i < 3; i++) {
					System.out.println("Digite sua senha:");
					String senha = sc.nextLine();
					senhaCorreta = vendedorService.conferirSenha(vendedor, senha);
						if(!senhaCorreta) {
							System.out.println("Senha incorreta, tente novamente");
						} else {
							break;
						}
					}
						if(!senhaCorreta) {
							break;
						}
					
					Menu.menuVendedor1();
					opcao2 = sc.nextInt();
					if (opcao2 == 1) {
						vendedorService.mostrarAlugueisVeiculos(vendedor);
					} else if (opcao2 == 2) {
						vendedorService.verSalario(vendedor);
					} else if(opcao2 == 3) {
						vendedorService.verSalarioComComissao(vendedor);
					}
					break;
			case 3: 
				Menu.menuCliente1();
				email = sc.nextLine();
				
				Administrador admin = adminService.confereEmail(email);
				if(admin == null) {
					throw new SistemaException("Administrador não encontrado!");
				}
				senhaCorreta = false;
				for(int i = 0; i < 3; i++) {
				System.out.println("Digite sua senha:");
				String senha = sc.nextLine();
				senhaCorreta = adminService.conferirSenha(admin, senha);
					if(!senhaCorreta) {
						System.out.println("Senha incorreta, tente novamente");
					} else {
						break;
					}
				}
					if(!senhaCorreta) {
						break;
					}
					Menu.menuAdministrador();
					opcao2 = sc.nextInt();
					adminService.confereEntrada(opcao2);
					break;
			case 0: 
					continua = false;
					break;
			default:
				System.out.println("Alternativa inválida. Tente novamente!");
				break;
			}
			}catch(SistemaException e) {
				System.out.println(e.getMessage());
			}catch(InputMismatchException e) {
				System.out.println("Opção inválida!");
				sc.next();
			}finally {
				Thread.sleep(1500l);
			}
			
		} while(continua);
	}
		
}

