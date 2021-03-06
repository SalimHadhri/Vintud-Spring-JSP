package com.thp.project.vintud.dao.impl;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import com.thp.project.vintud.dao.AnnouncementDao;
import com.thp.project.vintud.dao.CategoryDao;
import com.thp.project.vintud.dao.UserDao;
import com.thp.project.vintud.dao.factory.DAOFactory;
import com.thp.project.vintud.entity.Announcement;
import com.thp.project.vintud.entity.User;



public class AnnouncementDaoImpl implements AnnouncementDao{

    Connection con = DAOFactory.connect();
    //ConnectionManager.getInstance().getConnection();

	CategoryDao categoryDao = DAOFactory.getCategoryDAO() ;
	UserDao userDAO = DAOFactory.getUserDAO() ;
	
    ResultSet résultats = null;
    String requete = "";
    ResultSetMetaData rsmd;
    
    
    
 	
	
    private static void arret(String message) {
        System.err.println(message);
        System.exit(99);
     }
    
    private static void affiche(String message) {
        System.out.println(message);
     }
    
 
	public AnnouncementDaoImpl() {
	}

	
	public Announcement findAnnounceById (int id) {
		
		Announcement annonce = new Announcement() ;
		requete = "SELECT * FROM vintud.announcement WHERE id ="+id + "  ;" ;
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
				
	         boolean encore = résultats.next();
			if(encore) {				
				  annonce.setId_annoucement(résultats.getInt("id"));
				  annonce.setTitle(résultats.getString("title"));
				  annonce.setDescription(résultats.getString("description"));
				  annonce.setCategory_id(résultats.getInt("category_id"));
				  annonce.setPrice(résultats.getInt("price"));
				  annonce.setPicture(résultats.getString("picture"));
				  annonce.setPublication_date(résultats.getString("publication_date"));
				  annonce.setIs_available(résultats.getBoolean("is_available"));
				  annonce.setView_number(résultats.getInt("view_number"));
				  annonce.setLocalisation(résultats.getString("localisation"));
				  annonce.setUser_id(résultats.getInt("user_id") );
			   	}
			   
			   résultats.close();
			   
		} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
		}
		return annonce ;
		
		
	}
	
	
	
	public void findAnnouncementByNameCatgeryAndOrPrice () {
		
		System.out.println("****Commencons notre recherche de l'annonce *****");
		
		System.out.println("  Entrez le nom de l'annonce ");
		Scanner sc1 = new Scanner(System.in);
		String nomAnnonce= sc1.nextLine();
		
		System.out.println("**********choisissez entre ces categories******");
		categoryDao.AfficherCategories();
		
		
		System.out.println("  Entrez la category de l'annonce ");
		Scanner sc2 = new Scanner(System.in);
		int categoriId= sc2.nextInt();
		
		System.out.println("  Entrez le prix de notre annonce  NB : O pour ne pas rechercher avec le prix");
		Scanner sc3 = new Scanner(System.in);
		String prixString = sc3.nextLine()  ;
    	Scanner scanner = new Scanner(prixString);
    	scanner.useLocale(Locale.US);
		float prixCategoryFoat= scanner.nextFloat() ;
		

		if(prixCategoryFoat !=0) {
			
		
		requete = "SELECT * FROM vintud.announcement WHERE title  ILIKE '%"+nomAnnonce+"%' "
				+ "AND category_id="+ categoriId +" AND price = "+ prixCategoryFoat  ;
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
				
	         boolean encore = résultats.next();
	         
	         if (encore) {
	        	 
	       
			  while (encore) {
				   System.out.println("*********** new announcement ********");
				   System.out.println(résultats.getInt("id")
					+"\n"+résultats.getString("title")
					+"\n"+résultats.getString("description")
					+"\n"+résultats.getInt("category_id")
					+"\n"+résultats.getFloat("price")
					+"\n"+résultats.getByte("picture")
					+"\n"+résultats.getTimestamp("publication_date")
					+"\n"+résultats.getBoolean("is_available")
					+"\n"+résultats.getInt("view_number")
					+"\n"+résultats.getString("localisation")
					+"\n"+résultats.getInt("user_id") );
				   encore = résultats.next();
			   }
	         } else {
	        	 affiche("pas d'annonce correspondante !!");
	         }
			   
			  résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		affiche("fin du programme");
	    System.exit(0);  
		}else {

			requete = "SELECT * FROM vintud.announcement WHERE title ILIKE '%"+nomAnnonce+"%' "
					+ "AND category_id="+ categoriId  ;
			try {
		         Statement stmt = con.createStatement();
		         résultats = stmt.executeQuery(requete);
					
		         boolean encore = résultats.next();
		         
		         if (encore) {
		        	 
		       
				  while (encore) {
					   System.out.println("*********** new announcement ********");
					   System.out.println(résultats.getInt("id")
						+"\n"+résultats.getString("title")
						+"\n"+résultats.getString("description")
						+"\n"+résultats.getInt("category_id")
						+"\n"+résultats.getFloat("price")
						+"\n"+résultats.getByte("picture")
						+"\n"+résultats.getTimestamp("publication_date")
						+"\n"+résultats.getBoolean("is_available")
						+"\n"+résultats.getInt("view_number")
						+"\n"+résultats.getString("localisation")
						+"\n"+résultats.getInt("user_id") );
					   encore = résultats.next();
				   }
		         } else {
		        	 affiche("pas d'annonce correspondante !!");
		         }
				   
				  résultats.close();
				} catch (SQLException e) {
					arret("Anomalie lors de l'execution de la requęte") ;
				}
			affiche("fin du programme");
		    System.exit(0);  
		}
	}
	
	public void filtreAnnoucementPrix () {
		
		requete = "SELECT * FROM vintud.announcement  ORDER BY price ; ";
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
			 boolean encore = résultats.next();
			 while (encore) {
				   System.out.println("*********** new announcement ********");
				   System.out.println(résultats.getInt("id")
					+"\n"+résultats.getString("title")
					+"\n"+résultats.getString("description")
					+"\n"+résultats.getInt("category_id")
					+"\n"+"that's the price : "+résultats.getFloat("price")
					+"\n"+résultats.getByte("picture")
					+"\n"+résultats.getTimestamp("publication_date")
					+"\n"+résultats.getBoolean("is_available")
					+"\n"+résultats.getInt("view_number")
					+"\n"+résultats.getString("localisation")
					+"\n"+résultats.getInt("user_id") );
				   
				   encore = résultats.next();
			   }
			   résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		
	}
	
	public void filtreAnnoucementLocalisation () {
		
		requete = "SELECT * FROM vintud.announcement  ORDER BY localisation ; ";
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
			 boolean encore = résultats.next();
			 while (encore) {
				   System.out.println("*********** new announcement ********");
				   System.out.println(résultats.getInt("id")
					+"\n"+résultats.getString("title")
					+"\n"+résultats.getString("description")
					+"\n"+résultats.getInt("category_id")
					+"\n"+résultats.getFloat("price")
					+"\n"+résultats.getByte("picture")
					+"\n"+résultats.getTimestamp("publication_date")
					+"\n"+résultats.getBoolean("is_available")
					+"\n"+résultats.getInt("view_number")
					+"\n"+"that's the localisation : "+résultats.getString("localisation")
					+"\n"+résultats.getInt("user_id") );
				   
				   encore = résultats.next();
			   }
			   résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		
	}

	public void voirNombreVues (String email, String pwd) {
		
		User userConnected = userDAO.connectAccount(email,pwd) ;
		
		requete = "SELECT * FROM vintud.announcement WHERE user_id = "+userConnected.getId_user()+"; ";
		
		
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
			 boolean encore = résultats.next();
			 while (encore) {
				 System.out.println("***********voici le nombre de vue pour chacune de vos annonces *************");
				   System.out.println("*********** new announcement ********");
				   System.out.println("id announcement : "+résultats.getInt("id")
					+"\n"+"title of your annoucement : " +  résultats.getString("title")
					+"\n" +" description of your annoucement : "  +résultats.getString("description")
					+"\n"+" number of view of your annoucement : " +résultats.getInt("view_number"));
				   
				   encore = résultats.next();
			   }
			   résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		
	}

	public ArrayList<Announcement> findAnnounceByIdUser(int id){
		
		ArrayList<Announcement> listAnnonces = new ArrayList<Announcement>() ;
		
		requete = "SELECT * FROM vintud.announcement WHERE user_id ="+id+"  ; ";
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
				
	         
	         boolean encore = résultats.next();
			  while (encore) {
				  Announcement annonce = new Announcement() ;
				  annonce.setId_annoucement(résultats.getInt("id"));
				  annonce.setTitle(résultats.getString("title"));
				  annonce.setDescription(résultats.getString("description"));
				  annonce.setCategory_id(résultats.getInt("category_id"));
				  annonce.setPrice(résultats.getInt("price"));
				  annonce.setPicture(résultats.getString("picture"));
				  annonce.setPublication_date(résultats.getString("publication_date"));
				  annonce.setIs_available(résultats.getBoolean("is_available"));
				  annonce.setView_number(résultats.getInt("view_number"));
				  annonce.setLocalisation(résultats.getString("localisation"));
				  annonce.setUser_id(résultats.getInt("user_id") );
				  
				  listAnnonces.add(annonce) ;	
				   encore = résultats.next();
			   }
			   
			  résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		
		return listAnnonces ;
	}
	
	public ArrayList<Announcement> findAllAnnoucement(){
		
		ArrayList<Announcement> listAnnonces = new ArrayList<Announcement>() ;
		
		requete = "SELECT * FROM vintud.announcement  ; ";
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
				
	         
	         boolean encore = résultats.next();
			  while (encore) {
				  Announcement annonce = new Announcement() ;
				  annonce.setId_annoucement(résultats.getInt("id"));
				  annonce.setTitle(résultats.getString("title"));
				  annonce.setDescription(résultats.getString("description"));
				  annonce.setCategory_id(résultats.getInt("category_id"));
				  annonce.setPrice(résultats.getInt("price"));
				  annonce.setPicture(résultats.getString("picture"));
				  annonce.setPublication_date(résultats.getString("publication_date"));
				  annonce.setIs_available(résultats.getBoolean("is_available"));
				  annonce.setView_number(résultats.getInt("view_number"));
				  annonce.setLocalisation(résultats.getString("localisation"));
				  annonce.setUser_id(résultats.getInt("user_id") );
				  
				  listAnnonces.add(annonce) ;	
				   encore = résultats.next();
			   }
			   
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
		
		return listAnnonces ;
		
	}
	
public void consulterAnnonces () {
		
		
    	requete = "SELECT * FROM vintud.announcement ; ";
		try {
	         Statement stmt = con.createStatement();
	         résultats = stmt.executeQuery(requete);
			 boolean encore = résultats.next();
			 while (encore) {
				   System.out.println("*********** new announcement ********");
				   System.out.println(résultats.getInt("id")
					+"\n"+résultats.getString("title")
					+"\n"+résultats.getString("description")
					+"\n"+résultats.getInt("category_id")
					+"\n"+résultats.getInt("price")
					+"\n"+résultats.getString("picture")
					+"\n"+résultats.getString("publication_date")
					+"\n"+résultats.getBoolean("is_available")
					+"\n"+résultats.getInt("view_number")
					+"\n"+résultats.getString("localisation")
					+"\n"+résultats.getInt("user_id") );
				   
				   encore = résultats.next();
			   }
			   résultats.close();
			} catch (SQLException e) {
				arret("Anomalie lors de l'execution de la requęte") ;
			}
	}

	@Override
	public int addAnnouncement(Announcement announcement) {

		  
		int status = 0 ;
		
			
		String title = "'"+announcement.getTitle()+"'" ;
		String description ="'"+announcement.getDescription()+"'" ;
		String picture ="'"+ announcement.getPicture() +"'";
		String date = "'"+announcement.getPublication_date() +"'";
		String localisation = "'"+announcement.getLocalisation() + "'" ;
		
		
		
		
		
		requete="INSERT INTO vintud.announcement values ("+announcement.getId_annoucement()+","+ title+","+description+","+announcement.getCategory_id()+","+announcement.getPrice()+","+picture+","+date+","+announcement.isIs_available()+","+announcement.getView_number()+","+localisation+","+announcement.getUser_id() +");" ;



		try {
			Statement stmt = con.createStatement();
	        status = stmt.executeUpdate(requete) ;        
	        System.out.println("Your profile has been saved !! Congrat ;)  ");
		} 
		catch (SQLException e) {
			status=-1 ;
		}
		affiche("fin du programme");
		return status ;
		
	}
	

	}

	

	

    

