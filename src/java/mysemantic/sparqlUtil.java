/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysemantic;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class sparqlUtil {
  public ResultSet execute(Model model, String queryString) //method untuk mengeksekusi SPARQL
    {
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        return qexec.execSelect();
    }

    public Document prepareResult(ResultSet rs,boolean asRDF)
    throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if(asRDF)
        {
            ResultSetFormatter.toModel(rs).write(out,"RDF/XML-ABBREV");
        }
        else
        {
        ResultSetFormatter.outputAsXML(out,rs);
        }
        return DocumentHelper.parseText(out.toString());
        }

    public String[] parseProperties(Document d)
    {
     List list= d.getRootElement().element("results").elements("result");
     String[] temp=new String[list.size()];

     for(int i=0; i <list.size();i++)
     {
         Element elm = (Element) list.get(i);
         temp[i] = elm.element("binding").element("uri").getData().toString();

     }
     return temp;
    }  
}
