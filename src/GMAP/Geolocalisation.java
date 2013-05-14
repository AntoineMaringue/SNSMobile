package GMAP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe permettant de g�olocaliser les entreprises sur une carte google map
 * auto-g�n�r�e
 * 
 * @author Antoine Maringue
 * 
 */
public final class Geolocalisation {

	// ATTRIBUTS
	/**
	 * le nom du fichier temporaire
	 */
	private final String _nameFile = "tmpMaps.html";
	/**
	 * Le charset utilis� lors de l'envoi de notre requete http
	 */
	private static final String charset = "UTF-8";
	/**
	 * La cl� d'activation de l'api google
	 */
	private final String _googleKey = "AIzaSyD30iCogjIiDsPSx7dF2h4MUXlHtTd7S78";
	/**
	 * Le contenu du fichier que nous allons cr�er
	 */
	private String _fileContent = null;
	/**
	 * Les informations qui apparaitront sur la carte concernant le site
	 * recherch�
	 */
	private String _infoBulle = null;

	/**
	 * Construction d'un tableau pour la map
	 */
	private Map<String, Coordonnees> coordMap;

	/**
	 * Le tableau comportant la liste des associations
	 */
	private AbstractList<String> associationList;
	
	/**
	 * Nom du contact principal du d�partment
	 */
	private String nameContact = "";
	/**
	 * Ville principal du d�partment
	 */
	private String cityContact = "";
	/**
	 * Fax du contact principal du d�partment
	 */
	private String faxContact = "";
	/**
	 * Mail du contact principal du d�partment
	 */
	private String mailContact = "";
	/**
	 * Adresse du siege social du d�partment
	 */
	private String addressContact = "";
	/**
	 * T�l�phone du si�ge social du d�partment
	 */
	private String phoneContact = "";
	/**
	 * Le document que nous renverra le serveur http ici la page du site web des restos du coeurs
	 */
	private Document doc;

	// CONSTRUCTEUR
	/**
	 * Constructeur de la g�olocalisation
	 * 
	 */
	public Geolocalisation() {
		_fileContent = "";
		_infoBulle = "";
	}

	// METHODES
	/**
	 * Initialisation des informations + g�n�ration des donn�es pour constituer une map
	 * 
	 * @param departement : un d�partement 01 ....17 ..... 69 ....
	 */
	public String searchGeoLoc(String departement) {

		String trace = "";

		infosDepartementSearch(departement);

		coordMap = new HashMap<String, Coordonnees>();
		for (String city : associationList) {
			buildCoordonneesCities(city);
		}

		generateMapToSite();

		return trace;
	}

	/**
	 * Initialisatiopn des diff�rentes informations des sites recherch�es sur le d�partement demand�
	 * @param departement : un d�partement 01 ....17 ..... 69 ....
	 */
	private void infosDepartementSearch(String departement) {

		try {
			HttpURLConnection connection = null;
			BufferedReader serverResponse = null;

			String replyServer = "";

			// OPEN CONNECTION
			connection = (HttpURLConnection) new URL(
					"http://www.restosducoeur.org/francemap?dep="
							+ URLEncoder.encode(departement, charset)
							+ "#regions-liste-restos&charset=UTF-8")
					.openConnection();

			// RESPONSE STREAM
			serverResponse = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// READ THE RESPOSNE
			String line;
			while ((line = serverResponse.readLine()) != null) {
				replyServer += line + "\n";
			}
			parsingHttpResponse(replyServer);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Initialisation des coordonn�es du site
	 * @param city
	 */
	private void buildCoordonneesCities(String city) {
		// D�claration des objets
		String trace = "";
		URLConnection urlForMap = null;
		BufferedReader buffReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;

		try {
			// Construction de notre requete au serveur http
			urlForMap = new URL("http://maps.google.fr/maps/geo" + "?"
					+ buildRequestString(city, _googleKey)).openConnection();

			// R�cup�ration de la reponse du serveur HTTP � notre requete
			inputStream = urlForMap.getInputStream();

			// Encapsulation dans un flux de lecture
			inputStreamReader = new InputStreamReader(inputStream);

			// Encapsulation du lecteur dans un buffer
			buffReader = new BufferedReader(inputStreamReader);

			trace = buffReader.readLine();

			// On parse notre r�ponse pour r�cup�rer la latitude et la longitude
			parseResponse(trace, city);

		}
		// URL MAL STRUCTUREE ...
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// AUTRES EXCEPTIONS ...
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Cr�ation de la query correcte
	 * 
	 * @param address
	 *            : l'adresse de l'association dans la base
	 * @param keyGoogle
	 *            : la cl� google permettant de g�olocaliser
	 * @return : la requete au bon format
	 */
	private static String buildRequestString(String address, String keyGoogle) {
		// D�claration des objets
		String[] params = null;
		String query = "";

		// Nous somme oblig� de passer par ce syst�me pour que l'url
		// soit correctement comprise lors de l'envoi de la requete au serveur
		// http
		try {
			params = new String[2];
			params[0] = address;
			params[1] = keyGoogle;

			query = String.format("q=%s&output=csv&key=%s",
					URLEncoder.encode(params[0], charset),
					URLEncoder.encode(params[1], charset));
		}

		// ENCODING NON SUPPORTE
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return query;
	}

/**
 * Cr�ation de l'infoBulle concernant l'association s�lectionn�e sur la carte
 */
	private void createdInfoBulle() 
	{
		// Construction des informations concernant la soci�t�
		_infoBulle += "var contentString = '<div id=\"content\">'+";
		_infoBulle += "'<div id=\"siteNotice\">'+";
		_infoBulle += "'</div>'+";
		_infoBulle += "'<h2 id=\"firstHeading\" class=\"firstHeading\">"
				+ "ASSOCIATIONS " + "</h2>'+";

		_infoBulle += "'<div id=\"bodyContent\">'+";

		_infoBulle += "\"";
		

		_infoBulle += "\\n"+nameContact+"\\n" + "\""+" +\n";
		_infoBulle += "\""+addressContact+"\\n" + "\""+"+\n";
		_infoBulle += "\""+cityContact+"\\n" + "\""+"+ \n";
		_infoBulle += "\""+phoneContact+"\\n" + "\""+" +\n";
		_infoBulle += "\""+faxContact+"\\n" + "\""+"+ \n";
		_infoBulle += "\""+mailContact+"\\n" + "\""+"\n";

		_infoBulle += "";
		_infoBulle += "+'</p>'+";
		_infoBulle += "'';"+"\n";

		// On met toutes les informations de l'infobulle dans une variable
		_infoBulle += "var infowindow = new google.maps.InfoWindow({";
		_infoBulle += "content: contentString";
		_infoBulle += "});";

		// Ajout des informations sur lev� de l'�v�nement onclick
		_infoBulle += "google.maps.event.addListener(marker, 'click', function() {";
		_infoBulle += "infowindow.open(map,marker);";
		_infoBulle += "});";

	}

	private void parsingHttpResponse(String responseHttp) 
	{
		doc = Jsoup.parse(responseHttp, charset);

		Elements classSearch = doc.getElementsByClass("adresse");
		Elements lstCities = doc.getElementsByClass("liste-actions");
		Elements cities = lstCities.get(0).getAllElements();
		for (Element link : classSearch) {
			String infosContactDepartement = link.text();
			String[] array = infosContactDepartement.split(" ");

			boolean step1 = false;
			int currentIdx = 0, idFax = 0, idPhone = 0, idMail = 0;
			for (int i = 0; i < array.length; i++) {
				if (i == 0 || i == 1) {
					nameContact += array[i] + " ";
				} else if (!step1) {
					if (array[i].equalsIgnoreCase("-")) {
						step1 = true;
						currentIdx = i;
					}
					if (!step1)
						addressContact += array[i] + " ";
				} else if (step1 && currentIdx > 0) {
					cityContact += array[i];
					currentIdx = 0;
				} else {
					if (idPhone <= 2) {
						idPhone++;
						phoneContact += array[i];
					} else if (idFax <= 2) {
						idFax++;
						faxContact += array[i];
					} else if (idMail <= 2) {
						idMail++;
						mailContact += array[i];
					}
				}
			}
			String tmp = getMailContact();
			setMailContact("<a href=mailto:" + tmp.split(":")[1] + ">" + tmp
					+ "</a>");
		}
		for (Element link : cities) {
			String citiesAlimentaryHelp = link.text();
			citiesAlimentaryHelp.subSequence(40, citiesAlimentaryHelp.length());
			citiesAlimentaryHelp.toString();

			String[] part = citiesAlimentaryHelp.split("[:]");
			String lst = part[1].split("Repas")[0];
			associationList = new ArrayList<String>();
			for (String city : lst.split("-")) {
				associationList.add(city);
			}
			break;
		}
	}

	/**
	 * Retourne le nom du contact principal du d�partement
	 * @return
	 */
	public String getNameContact() {
		return nameContact;
	}

	/**
	 * Retourne la ville du si�ge social du d�partement
	 * @return
	 */
	public String getCityContact() 
	{
		return cityContact;
	}

	/**
	 * Retourne le fax du si�ge social du d�partement
	 * @return
	 */
	public String getFaxContact() 
	{
		return faxContact;
	}

	public void setFaxContact(String faxContact) {
		this.faxContact = faxContact;
	}

	/**
	 * Retourne le mail du si�ge social du d�partement
	 * @return
	 */
	public String getMailContact() {
		return mailContact;
	}

	/**
	 * Modifie le mail du si�ge social du d�partement
	 * @return
	 */
	public void setMailContact(String mailContact) {
		this.mailContact = mailContact;
	}

	/**
	 * R�cup�ration de la longitude et de la latitude
	 * 
	 * @param responseHttp
	 *            : le contenu de la reponse du serveur http
	 */
	private void parseResponse(String responseHttp, String city) 
	{
		// D�claration des objets
		String lat, lon;

		// On parse le fichier csv
		lat = responseHttp.split(",")[2];
		lon = responseHttp.split(",")[3];

		coordMap.put(
				city,
				new Coordonnees(Double.parseDouble(lat), Double
						.parseDouble(lon)));

	}

	/**
	 * Ecriture dans un fichier au format HTML pour g�n�rer la map contenant le
	 * site
	 * 
	 * @param nameFile
	 *            : nom du fichier qui contiendra les coordonn�es du site
	 * @param lineCode
	 *            : Ce que nous voulons que contienne le fichier (ligne du
	 *            fichier)
	 */
	private void writeInFileMap(String nameFile, String lineCode) 
	{
		// D�claration de nos objets que nous voulons instancier
		String adressedufichier = "";
		FileWriter fw = null;
		BufferedWriter buffWriter = null;

		// on met try si jamais il y a une exception
		try {
			// on va chercher le chemin et le nom du fichier et on me tout ca
			// dans un String
			adressedufichier = "c:\\temp\\" + nameFile;

			fw = new FileWriter(adressedufichier, true);

			// le BufferedWriter qui permetrra d'�crire directement dans le
			// fichier
			buffWriter = new BufferedWriter(fw);

			// on marque dans le fichier ou plutot dans le BufferedWriter qui
			// sert comme un tampon(stream)
			buffWriter.write(lineCode + "\n");

			// On ajoute le contenu au buffer
			_fileContent += lineCode;

			// ensuite flush envoie dans le fichier, ne pas oublier cette
			// methode pour le BufferedWriter cela permet de liberer le flux
			buffWriter.flush();

			// et on le ferme le flux de donn�es
			buffWriter.close();

		} catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}
	}

	/**
	 * G�n�ration d'une carte google map avec les diff�rentes associations
	 */
	private void generateMapToSite() 
	{
		
		createdInfoBulle();
		
		// D�claration des objets
		String filePath = "";
		File fileMap = null;

		// Si le fichier existe on le supprime
		filePath = "c:\\temp\\" + _nameFile;
		fileMap = new File(filePath);
		if (fileMap.exists()) {
			fileMap.delete();
			_fileContent = "";
		}

		writeInFileMap(_nameFile, "<!DOCTYPE html> ");
		writeInFileMap(_nameFile, " <html>");
		writeInFileMap(_nameFile, " <head>");
		writeInFileMap(_nameFile,
				"  <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\" />");
		writeInFileMap(_nameFile, " <style type=\"text/css\">");
		writeInFileMap(_nameFile, "  html { height: 100% }");
		writeInFileMap(_nameFile,
				"  body { height: 100%; margin: 0; padding: 0 }");
		writeInFileMap(_nameFile, "  #map_canvas { height: 100% }");
		writeInFileMap(_nameFile, " </style>");

		// L'API GOOGLE MAP
		writeInFileMap(_nameFile, "<script type=\"text/javascript\"");
		writeInFileMap(_nameFile,
				" src=\"https://maps.googleapis.com/maps/api/js?key="
						+ _googleKey + "&sensor=false\"> ");
		writeInFileMap(_nameFile, " </script> ");

		Coordonnees m = coordMap.values().iterator().next();
		// D�claration de notre fonction servant � fabriquer la carte
		writeInFileMap(_nameFile, " <script type=\"text/javascript\">");
		writeInFileMap(_nameFile, "var map = null; ");
		writeInFileMap(_nameFile, "var markerArray = []; ");
		writeInFileMap(_nameFile, " function initialize() { ");
		writeInFileMap(_nameFile, " var myOptions = { ");
		writeInFileMap(_nameFile, " zoom: 9,");
		writeInFileMap(_nameFile,
				" center: new google.maps.LatLng(" + m.get_latitude() + ", "
						+ m.get_longitude() + "),");
		writeInFileMap(_nameFile, " mapTypeControl: true,");
		writeInFileMap(_nameFile, " mapTypeControlOptions: { ");
		writeInFileMap(_nameFile,
				" style: google.maps.MapTypeControlStyle.DROPDOWN_MENU ");
		writeInFileMap(_nameFile, "},");
		writeInFileMap(_nameFile, " navigationControl: true, ");
		writeInFileMap(_nameFile, " mapTypeId: google.maps.MapTypeId.ROADMAP ");
		writeInFileMap(_nameFile, "}");
		writeInFileMap(
				_nameFile,
				" map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions);");

		writeInFileMap(_nameFile,
				"google.maps.event.addListener(map, 'click', function() { ");
		writeInFileMap(_nameFile, "infowindow.close(); ");
		writeInFileMap(_nameFile, "}); ");

		Iterator<Entry<String, Coordonnees>> it = coordMap.entrySet().iterator();
		Iterator<Coordonnees> c = coordMap.values().iterator();
		while (it.hasNext()) {
			Object city = it.next().toString().split("=")[0];
			Coordonnees coord = (Coordonnees) c.next();
			writeInFileMap(_nameFile, " createMarker(new google.maps.LatLng("
					+ coord.get_latitude() + "," + coord.get_longitude()
					+ "),\"" + city + "\"+\n\"" + nameContact + "\"+\n\""
					+ addressContact + cityContact + "\"+\n\"" + faxContact
					+ "\"+\n\"" + phoneContact + "\"+\n\"" + mailContact
					+ "\"," + "0," + "null); ");

		}

		writeInFileMap(_nameFile, "mc.addMarkers(markerArray, true); ");
		writeInFileMap(_nameFile, "}");

		writeInFileMap(_nameFile,
				"var infowindow = new google.maps.InfoWindow({ ");
		writeInFileMap(_nameFile, "size: new google.maps.Size(150, 50) ");
		writeInFileMap(_nameFile, "});");

		writeInFileMap(_nameFile,
				"function createMarker(latlng, myTitle, myNum, myIcon) { ");
		writeInFileMap(_nameFile, "var contentString = myTitle; ");
		writeInFileMap(_nameFile, "var marker = new google.maps.Marker({ ");
		writeInFileMap(_nameFile, "position: latlng,");
		writeInFileMap(_nameFile, "map: map,");
		writeInFileMap(_nameFile, "icon: myIcon, ");
		writeInFileMap(_nameFile,
				"zIndex: Math.round(latlng.lat() * -100000) << 5, ");
		writeInFileMap(_nameFile, "title: myTitle ");
		writeInFileMap(_nameFile, " }); ");

		writeInFileMap(_nameFile, _infoBulle);

		writeInFileMap(_nameFile, " markerArray.push(marker); ");
		writeInFileMap(_nameFile, "}");
		writeInFileMap(_nameFile, " window.onload = initialize;");
		writeInFileMap(_nameFile, "</script>");

		writeInFileMap(_nameFile, "<body>");
		writeInFileMap(_nameFile,
				"<div id=\"map_canvas\" style=\"width:100%; height:100%\"></div>");
		writeInFileMap(_nameFile, "</body>");
		writeInFileMap(_nameFile, "</html>");
	}

	/**
	 * Retourne le contenu de la carte HTML
	 * 
	 * @return
	 */
	public String getFileContent() 
	{
		return _fileContent;
	}
}
