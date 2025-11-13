    package bioprint.modulousuarios;

    import java.util.Scanner;

    public class MenuUsuarios {
        public static boolean menu(UsuarioService servicio,boolean[] salir, Notificador bot, String[] nombre){
            Scanner sc = new Scanner(System.in);
            int opcion=0;
            while(true){
                System.out.println("-----------------------------------------------------------------");
                System.out.println("Que desea hacer?\n1.Iniciar sesion\n2.Registrarse\n3.Salir del programa");
                opcion=sc.nextInt();
                sc.nextLine();
                if(opcion<1||opcion>3){
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Por favor seleccionar entre las opciones dadas");
                    continue;
                }
                break;
            }
            if(opcion==3){
                salir[0]=true;
                return true;
            }
            String contrasena="";
            if(opcion==1){
                while(true){
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("ingrese su usuario (o \"salir\" para volver)");
                    nombre[0]=sc.nextLine();
                    if(nombre[0].equals("salir")){
                        return false;
                    }
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("ingrese su contrasena (o \"salir\" para volver)");
                    contrasena=sc.nextLine();
                    if(contrasena.equals("salir")){
                        return false;
                    }
                    if(servicio.validarUsuario(nombre[0], contrasena)){
                        System.out.println("-----------------------------------------------------------------");
                        System.out.println("Sesion iniciada como: "+nombre[0]);
                        bot.enviarMensaje("Usuario "+nombre[0]+" acaba de iniciar sesion");
                        return true;
                    }
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Usuario o contraseña invalidos, volver a intentar");
                }
            }
            Usuario user=new Usuario();
            System.out.println("-----------------------------------------------------------------");
            System.out.println("ingrese el nuevo usuario (o \"salir\" para volver)");
            nombre[0]=sc.nextLine();
            if(nombre[0].equals("salir")){
                return false;
            }
            user.setNombre(nombre[0]);
            System.out.println("-----------------------------------------------------------------");
            System.out.println("ingrese la contrasena (o \"salir\" para volver)");
            contrasena=sc.nextLine();
            if(contrasena.equals("salir")){
                return false;
            }
            user.setContrasena(contrasena);
            servicio.guardar(user);
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Usuario creado, iniciando Sesion");

            bot.enviarMensaje("Nuevo usuario agregado a la base de datos: "+nombre[0]);
            return true;
        }
    }

