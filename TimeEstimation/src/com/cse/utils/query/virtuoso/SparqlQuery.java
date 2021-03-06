package com.cse.utils.query.virtuoso;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import com.cse.utils.query.virtuoso.bean.DoubleColumn;
import com.cse.utils.query.virtuoso.bean.SingleColumn;
import com.cse.utils.query.virtuoso.bean.TripleColumn;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

public class SparqlQuery {
	private VirtGraph virtGraph;

	private SparqlQuery() {
	}

	/**
	 * 构造函数的参数
	 * 
	 * @param url
	 *            链接数据图的url（形如"jdbc:virtuoso://localhost:1111"）
	 * @param graphName
	 *            图名
	 * @param username
	 *            virtuoso用户名（默认dba）
	 * @param password
	 *            virtuoso密码（默认dba）
	 */
	public SparqlQuery(String url, String graphName, String username,
			String password) {
		this.virtGraph = new VirtGraph(graphName, url, username, password);

	}
    
	public SparqlQuery(VirtGraph virtGraph){
		this.virtGraph=virtGraph;
	}
	
	public VirtGraph getVirtGraph(){
		return virtGraph;
	}
	/**
	 * 得到当前的Virtuoso数据库中的所有图
	 * 
	 * @return
	 */
	public List<String> queryAllGraphs() {
		String queryString = "SELECT DISTINCT ?g WHERE {  graph ?g { ?s ?p ?o }}";
		Query sparql = QueryFactory.create(queryString);

		QueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql,
				virtGraph);

		ResultSet results = vqe.execSelect();

		// 保存查询得到的结果
		List<String> list = new ArrayList<String>();

		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode graphName = result.get("g");

			list.add(graphName.toString());
		}

		return list;
	}

	/**
	 * Virtuoso Sparql查询，返回一列
	 * 
	 * @param queryString
	 *            待查询的Sparql语句
	 * @param columnName
	 *            返回的列名
	 * @return
	 */
	public List<SingleColumn> querySingleColumn(String queryString,
			String columnName) {
		Query sparql = QueryFactory.create(queryString);

		QueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql,
				virtGraph);

		ResultSet results = vqe.execSelect();

		// 保存查询得到的结果
		List<SingleColumn> list = new ArrayList<SingleColumn>();

		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode columnValue = result.get(columnName);

			SingleColumn singleColumn = new SingleColumn();
			singleColumn.setFirstValue(columnValue.toString());

			list.add(singleColumn);
		}

		return list;
	}

	/**
	 * Virtuoso Sparql查询，返回两列
	 * 
	 * @param queryString
	 *            待查询的Sparql语句
	 * @param firstColumnName
	 *            返回的第一列列名
	 * @param secondColumnName
	 *            返回的第二列列名
	 * @param columnName
	 *            返回的列名
	 * @return
	 */
	public List<DoubleColumn> queryDoubleColumn(String queryString,
			String firstColumnName, String secondColumnName) {
		Query sparql = QueryFactory.create(queryString);

		QueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql,
				virtGraph);

		ResultSet results = vqe.execSelect();

		// 保存查询得到的结果
		List<DoubleColumn> list = new ArrayList<DoubleColumn>();

		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode firstColumnValue = result.get(firstColumnName);
			RDFNode secondColumnValue = result.get(secondColumnName);

			DoubleColumn doubleColumn = new DoubleColumn();
			doubleColumn.setFirstValue(firstColumnValue.toString());
			doubleColumn.setSecondValue(secondColumnValue.toString());

			list.add(doubleColumn);
		}

		return list;
	}

	/**
	 * Virtuoso Sparql查询，返回三列
	 * 
	 * @param queryString
	 *            待查询的Sparql语句
	 * @param firstColumnName
	 *            返回的第一列列名
	 * @param secondColumnName
	 *            返回的第二列列名
	 * @param thirdColumnName
	 *            返回的第三列列名
	 * @return
	 */
	public List<TripleColumn> queryTripleColumn(String queryString,
			String firstColumnName, String secondColumnName,
			String thirdColumnName) {
		Query sparql = QueryFactory.create(queryString);

		QueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql,
				virtGraph);

		ResultSet results = vqe.execSelect();

		// 保存查询得到的结果
		List<TripleColumn> list = new ArrayList<TripleColumn>();

		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode firstColumnValue = result.get(firstColumnName);
			RDFNode secondColumnValue = result.get(secondColumnName);
			RDFNode thirdColumnValue = result.get(thirdColumnName);

			TripleColumn tripleColumn = new TripleColumn();
			tripleColumn.setFirstValue(firstColumnValue.toString());
			tripleColumn.setSecondValue(secondColumnValue.toString());
			tripleColumn.setThirdValue(thirdColumnValue.toString());

			list.add(tripleColumn);
		}

		return list;
	}

	/**
	 * Virtuoso Sparql查询,两个实例之间的关系，谓语不能是type，宾语是实例 过滤掉概念层之间的关系，类公理，属性公理等
	 * http://www.w3.org过滤掉公理
	 * http://dbpedia.org/ontology过滤掉domain、range
	 * 
	 * @param args
	 */
	public List<DoubleColumn> queryRelation(String queryString,
			String firstColumnName, String secondColumnName) {
		Query sparql = QueryFactory.create(queryString);

		QueryExecution vqe = VirtuosoQueryExecutionFactory.create(sparql,
				virtGraph);

		ResultSet results = vqe.execSelect();

		// 保存查询得到的结果
		List<DoubleColumn> list = new ArrayList<DoubleColumn>();

		while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
			RDFNode firstColumnValue = result.get(firstColumnName);
			RDFNode secondColumnValue = result.get(secondColumnName);
			String property = firstColumnValue.toString();
			if (!property.startsWith("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
					&& secondColumnValue instanceof Resource) {
				DoubleColumn doubleColumn = new DoubleColumn();
				doubleColumn.setFirstValue(property);
				doubleColumn.setSecondValue(secondColumnValue.toString());
				list.add(doubleColumn);
			}

		}

		return list;
	}


	public static void main(String[] args) {
		SparqlQuery s = new SparqlQuery("jdbc:virtuoso://223.3.80.2:1111",
				"http://dbpedia.org", "dba", "dba");
		List<SingleColumn> list = s.querySingleColumn(
				"select distinct ?Concept where {[] a ?Concept} LIMIT 100",
				"Concept");
		for (SingleColumn i : list) {
			System.out.println(i.getFirstValue());
		}

		System.out.println("--------------------");
		System.out.println(s.queryAllGraphs());
	}
}
