package br.jus.trt9.acompspje.xml;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.jus.trt9.acompspje.db.ComposicaoSessao;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.db.VistaRegimental;

public class XMLUtils {
	private String xmlFile;
	
	public XMLUtils(String filename) {
		xmlFile = filename;
	}
	
	public List<RoteiroPautaSessao> readProcessos() throws SAXException, IOException, ParserConfigurationException, DOMException, ParseException {
		List<RoteiroPautaSessao> lista = new ArrayList<RoteiroPautaSessao>();
		
		// Carregar dados do arquivo XML com os dados da tabela pauta_sessao_roteiro
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getClassLoader().getResourceAsStream(xmlFile));
		NodeList nTables = doc.getElementsByTagName("TABLE");
		for (int t = 0; t < nTables.getLength(); t++) {
			Node nTable = nTables.item(t);
			
			if (nTable.getNodeType() == Node.ELEMENT_NODE && ((Element) nTable).getAttribute("NAME").equals("PAUTA_SESSAO_ROTEIRO")) {
				NodeList nRows = nTable.getChildNodes();
				
				for (int r = 0; r < nRows.getLength(); r++) {
					Node nRow = nRows.item(r);
					
					if (nRow.getNodeType() == Node.ELEMENT_NODE) {
						// Ler os campos do novo elemento RoteiroPautaSessao a partir dos elementos COLUMN
						int ID_SESSAO_PJE = 0;
						int ID_PROCESSO_PJE = 0;
						String DS_PROCESSO = null;
						String NM_RELATOR = null;
						String NM_REVISOR = null;
						String NM_TERCEIRO = null;
						Integer NR_ORDEM = null;
						String IN_TIPO_INCLUSAO = null;
						Character IN_DIVERGENCIA = null;
						Character IN_DESTAQUE = null;
						Character IN_SUSTENTACAO_ORAL = null;
						Character IN_PREFERENCIA = null;
						Date DT_SITUACAO_PAUTA = null;
						Date DT_PEDIDO_SUSTENTACAO_ORAL = null;
						String DS_ADV_SUSTENTACAO_ORAL = null;
						String DS_DISPOSITIVO = null;
						Character IN_REVISAO = null;
						Integer NR_SEQUENCIAL_PROCESSO = null;
						Date DT_ATUALIZACAO = null;
						String NM_USUARIO = null;
						Character IN_IMPEDIMENTO = null;
						Integer NR_SEQUENCIA = null;
						Integer NR_ANO = null;
						String DS_ACORDAO_COMPLETO = null;
						Integer ID_PAUTA_SESSAO = null;
						String DS_CLASSE_JUDICIAL = null;
						String DS_CLASSE_JUDICIAL_SIGLA = null;
						String NR_PROCESSO_CNJ = null;
						String DS_POLO_ATIVO = null;
						String DS_POLO_PASSIVO = null;
						String DS_ORGAO_JULGADOR_1GRAU = null;
						String DS_STATUS = null;
						String DS_DISPOSITIVO_SESSAO = null;
						Long CD_RELATOR = null;
						Long CD_REVISOR = null;
						Long CD_TERCEIRO = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
						NodeList nFilhos = nRow.getChildNodes();
						for (int j=0; j<nFilhos.getLength(); j++) {
							Node n = nFilhos.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) n;
								if (eElement.getTextContent().length() > 0) {
									if (eElement.getAttribute("NAME").equals("ID_SESSAO_PJE")) ID_SESSAO_PJE = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("ID_PROCESSO_PJE")) ID_PROCESSO_PJE = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DS_PROCESSO")) DS_PROCESSO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NM_RELATOR")) NM_RELATOR = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NM_REVISOR")) NM_REVISOR = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NM_TERCEIRO")) NM_TERCEIRO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NR_ORDEM")) NR_ORDEM = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("IN_TIPO_INCLUSAO")) IN_TIPO_INCLUSAO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("IN_DIVERGENCIA")) IN_DIVERGENCIA = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("IN_DESTAQUE")) IN_DESTAQUE = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("IN_SUSTENTACAO_ORAL")) IN_SUSTENTACAO_ORAL = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("IN_PREFERENCIA")) IN_PREFERENCIA = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("DT_SITUACAO_PAUTA")) DT_SITUACAO_PAUTA = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DT_PEDIDO_SUSTENTACAO_ORAL")) DT_PEDIDO_SUSTENTACAO_ORAL = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DS_ADV_SUSTENTACAO_ORAL")) DS_ADV_SUSTENTACAO_ORAL = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_DISPOSITIVO")) DS_DISPOSITIVO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("IN_REVISAO")) IN_REVISAO = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("NR_SEQUENCIAL_PROCESSO")) NR_SEQUENCIAL_PROCESSO = Integer.valueOf(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DT_ATUALIZACAO")) DT_ATUALIZACAO = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NM_USUARIO")) NM_USUARIO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("IN_IMPEDIMENTO")) IN_IMPEDIMENTO = eElement.getTextContent().charAt(0);
									if (eElement.getAttribute("NAME").equals("NR_SEQUENCIA")) NR_SEQUENCIA = Integer.valueOf(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NR_ANO")) NR_ANO = Integer.valueOf(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DS_ACORDAO_COMPLETO")) DS_ACORDAO_COMPLETO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("ID_PAUTA_SESSAO")) ID_PAUTA_SESSAO = Integer.valueOf(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DS_CLASSE_JUDICIAL")) DS_CLASSE_JUDICIAL = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_CLASSE_JUDICIAL_SIGLA")) DS_CLASSE_JUDICIAL_SIGLA = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NR_PROCESSO_CNJ")) NR_PROCESSO_CNJ = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_POLO_ATIVO")) DS_POLO_ATIVO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_POLO_PASSIVO")) DS_POLO_PASSIVO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_ORGAO_JULGADOR_1GRAU")) DS_ORGAO_JULGADOR_1GRAU = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_STATUS")) DS_STATUS = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_DISPOSITIVO_SESSAO")) DS_DISPOSITIVO_SESSAO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("CD_RELATOR")) CD_RELATOR = Long.parseLong(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("CD_REVISOR")) CD_REVISOR = Long.parseLong(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("CD_TERCEIRO")) CD_TERCEIRO = Long.parseLong(eElement.getTextContent());
								}
							}
						}
						lista.add(new RoteiroPautaSessao(ID_SESSAO_PJE, ID_PROCESSO_PJE, DS_PROCESSO, NM_RELATOR, NM_REVISOR, NM_TERCEIRO, NR_ORDEM, IN_TIPO_INCLUSAO, IN_DIVERGENCIA, IN_DESTAQUE, IN_SUSTENTACAO_ORAL, IN_PREFERENCIA, DT_SITUACAO_PAUTA, DT_PEDIDO_SUSTENTACAO_ORAL, DS_ADV_SUSTENTACAO_ORAL, DS_DISPOSITIVO, IN_REVISAO, NR_SEQUENCIAL_PROCESSO, DT_ATUALIZACAO, NM_USUARIO, IN_IMPEDIMENTO, NR_SEQUENCIA, NR_ANO, DS_ACORDAO_COMPLETO, ID_PAUTA_SESSAO, DS_CLASSE_JUDICIAL, DS_CLASSE_JUDICIAL_SIGLA, NR_PROCESSO_CNJ, DS_POLO_ATIVO, DS_POLO_PASSIVO, DS_ORGAO_JULGADOR_1GRAU, DS_STATUS, DS_DISPOSITIVO_SESSAO, CD_RELATOR, CD_REVISOR, CD_TERCEIRO));
					}	
				}
			}
		}
		
		return lista;
	}

	public SessaoJulgamento readSessao() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException {
		// Carregar dados do arquivo XML com os dados da tabela sessao_julgamento
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getClassLoader().getResourceAsStream(xmlFile));
		NodeList nTables = doc.getElementsByTagName("TABLE");
		for (int t = 0; t < nTables.getLength(); t++) {
			Node nTable = nTables.item(t);
			
			if (nTable.getNodeType() == Node.ELEMENT_NODE && ((Element) nTable).getAttribute("NAME").equals("SESSAO_JULGAMENTO")) {
				NodeList nRows = nTable.getChildNodes();
				
				for (int r = 0; r < nRows.getLength(); r++) {
					Node nRow = nRows.item(r);
					
					if (nRow.getNodeType() == Node.ELEMENT_NODE) {
						// Ler os campos do novo elemento SessaoJulgamento a partir dos elementos COLUMN
						int ID_SESSAO_PJE = 0;
						Date DT_SESSAO = null;
						int ID_ORGAO_JULGADOR_COLEGIADO = 0;
						Date DT_IMPORTACAO = null;
						String NM_USUARIO_IMPORTACAO = null;
						String DS_SALA = null;
						String DS_TIPO_SESSAO = null;
						String NM_PROCURADOR = null;
						Character IN_SEXO = null;
						Character IN_STATUS = null;
						Long CD_MAGISTRADO_PRESIDENTE = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
						NodeList nFilhos = nRow.getChildNodes();
						for (int j=0; j<nFilhos.getLength(); j++) {
							Node n = nFilhos.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) n;
								if (eElement.getTextContent().length() > 0) {
									if (eElement.getAttribute("NAME").equals("ID_SESSAO_PJE")) ID_SESSAO_PJE = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DT_SESSAO")) DT_SESSAO = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("ID_ORGAO_JULGADOR_COLEGIADO")) ID_ORGAO_JULGADOR_COLEGIADO = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("DT_IMPORTACAO")) DT_IMPORTACAO = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NM_USUARIO_IMPORTACAO")) NM_USUARIO_IMPORTACAO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_SALA")) DS_SALA = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DS_TIPO_SESSAO")) DS_TIPO_SESSAO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("NM_PROCURADOR")) NM_PROCURADOR = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("IN_SEXO")) IN_SEXO = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("IN_STATUS")) IN_STATUS = Character.valueOf(eElement.getTextContent().charAt(0));
									if (eElement.getAttribute("NAME").equals("CD_MAGISTRADO_PRESIDENTE")) CD_MAGISTRADO_PRESIDENTE = Long.parseLong(eElement.getTextContent());
								}
							}
						}
						
						// Preecher DT_SESSAO com a data atual caso ela não tenha sido passada no arquivo XML
						if (DT_SESSAO == null) {
							long time = Calendar.getInstance().getTimeInMillis();
							
							// Limpa os millisegundos para evitar problemas de arredondamento
							time = time - (time % 1000);
							
							DT_SESSAO = new Date(time);
						}
						
						return new SessaoJulgamento(ID_SESSAO_PJE, DT_SESSAO, ID_ORGAO_JULGADOR_COLEGIADO, DT_IMPORTACAO, NM_USUARIO_IMPORTACAO, DS_SALA, DS_TIPO_SESSAO, NM_PROCURADOR, IN_SEXO, IN_STATUS, CD_MAGISTRADO_PRESIDENTE);
					}	
				}
			}
		}
		
		return null;
	}

	public List<ComposicaoSessao> readComposicaoSessao() throws SAXException, IOException, ParserConfigurationException, DOMException, ParseException {
		List<ComposicaoSessao> lista = new ArrayList<ComposicaoSessao>();
		
		// Carregar dados do arquivo XML com os dados da tabela COMPOSICAO_SESSAO
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getClassLoader().getResourceAsStream(xmlFile));
		NodeList nTables = doc.getElementsByTagName("TABLE");
		for (int t = 0; t < nTables.getLength(); t++) {
			Node nTable = nTables.item(t);
			
			if (nTable.getNodeType() == Node.ELEMENT_NODE && ((Element) nTable).getAttribute("NAME").equals("COMPOSICAO_SESSAO")) {
				NodeList nRows = nTable.getChildNodes();
				
				for (int r = 0; r < nRows.getLength(); r++) {
					Node nRow = nRows.item(r);
					
					if (nRow.getNodeType() == Node.ELEMENT_NODE) {
						// Ler os campos do novo elemento ComposicaoSessao a partir dos elementos COLUMN
						int ID_COMPOSICAO_SESSAO = 0;
						int ID_SESSAO_PJE = 0;
						long CD_MAGISTRADO_TITULAR = 0;
						String NM_MAGISTRADO_TITULAR = null;
						Long CD_MAGISTRADO_SUBSTITUTO = null;
						String NM_MAGISTRADO_SUBSTITUTO = null;
						Date DT_ATUALIZACAO = null;
						String NM_USUARIO = null;
						Character IN_MARCADO_APTO = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.US);
						NodeList nFilhos = nRow.getChildNodes();
						for (int j=0; j<nFilhos.getLength(); j++) {
							Node n = nFilhos.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) n;
								if (eElement.getTextContent().length() > 0) {
									if (eElement.getAttribute("NAME").equals("ID_COMPOSICAO_SESSAO")) ID_COMPOSICAO_SESSAO = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("ID_SESSAO_PJE")) ID_SESSAO_PJE = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("CD_MAGISTRADO_TITULAR")) CD_MAGISTRADO_TITULAR = Long.parseLong(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NM_MAGISTRADO_TITULAR")) NM_MAGISTRADO_TITULAR = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("CD_MAGISTRADO_SUBSTITUTO")) CD_MAGISTRADO_SUBSTITUTO = Long.valueOf(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NM_MAGISTRADO_SUBSTITUTO")) NM_MAGISTRADO_SUBSTITUTO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("DT_ATUALIZACAO")) DT_ATUALIZACAO = dateFormat.parse(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("NM_USUARIO")) NM_USUARIO = eElement.getTextContent();
									if (eElement.getAttribute("NAME").equals("IN_MARCADO_APTO")) IN_MARCADO_APTO = eElement.getTextContent().charAt(0);
								}
							}
						}
						lista.add(new ComposicaoSessao(ID_COMPOSICAO_SESSAO, ID_SESSAO_PJE, CD_MAGISTRADO_TITULAR, NM_MAGISTRADO_TITULAR, CD_MAGISTRADO_SUBSTITUTO, NM_MAGISTRADO_SUBSTITUTO, DT_ATUALIZACAO, NM_USUARIO, IN_MARCADO_APTO));
					}	
				}
			}
		}
		
		return lista;
	}

	public List<VistaRegimental> readVistaRegimental() throws SAXException, IOException, ParserConfigurationException, DOMException, ParseException {
		List<VistaRegimental> lista = new ArrayList<VistaRegimental>();
		
		// Carregar dados do arquivo XML com os dados da tabela COMPOSICAO_SESSAO
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(getClass().getClassLoader().getResourceAsStream(xmlFile));
		NodeList nTables = doc.getElementsByTagName("TABLE");
		for (int t = 0; t < nTables.getLength(); t++) {
			Node nTable = nTables.item(t);
			
			if (nTable.getNodeType() == Node.ELEMENT_NODE && ((Element) nTable).getAttribute("NAME").equals("VISTA_REGIMENTAL")) {
				NodeList nRows = nTable.getChildNodes();
				
				for (int r = 0; r < nRows.getLength(); r++) {
					Node nRow = nRows.item(r);
					
					if (nRow.getNodeType() == Node.ELEMENT_NODE) {
						// Ler os campos do novo elemento ComposicaoSessao a partir dos elementos COLUMN
						int ID_PAUTA_SESSAO = 0;
						long CD_MAGISTRADO = 0;
						NodeList nFilhos = nRow.getChildNodes();
						for (int j=0; j<nFilhos.getLength(); j++) {
							Node n = nFilhos.item(j);
							if (n.getNodeType() == Node.ELEMENT_NODE) {
								Element eElement = (Element) n;
								if (eElement.getTextContent().length() > 0) {
									if (eElement.getAttribute("NAME").equals("ID_PAUTA_SESSAO")) ID_PAUTA_SESSAO = Integer.parseInt(eElement.getTextContent());
									if (eElement.getAttribute("NAME").equals("CD_MAGISTRADO")) CD_MAGISTRADO = Long.parseLong(eElement.getTextContent());
								}
							}
						}
						lista.add(new VistaRegimental(ID_PAUTA_SESSAO, CD_MAGISTRADO));
					}	
				}
			}
		}
		
		return lista;
	}

}
