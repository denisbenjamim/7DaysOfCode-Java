package br.com.sevendaysofcode.html;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;

import br.com.sevendaysofcode.Content;

public class HTMLGenerator implements AutoCloseable {
    
    final Writer writer;

    public HTMLGenerator(String nomeArquivo) throws FileNotFoundException {
        this.writer = new PrintWriter(nomeArquivo);
    }

    private HTMLElement createCard(Content content){
        HTMLElement divCard = new HTMLElement("div", "card text-white bg-dark mb-3 mr-2");
        HTMLElement h4CardHeader = new HTMLElement("h4", "card-header");
        HTMLElement divCardBody = new HTMLElement("div", "card-body");
        HTMLElement divCardFooter = new HTMLElement("div", "card-footer");
        HTMLElement imgCardImg = new HTMLElement("img", "card-img");
        HTMLElement pCardText = new HTMLElement("p", "card-text mt-2");
        HTMLElement pCardType = new HTMLElement("p", "card-text");

        h4CardHeader.textContent = content.title();
        pCardText.textContent = MessageFormat.format("classificacao: {0} - Ano: {1}", content.rating(), content.year());
        pCardType.textContent = MessageFormat.format("Tipo: {0}", content.type());
        
        divCard.addDataset("style", "max-width: 18rem");
        imgCardImg.addDataset("src", content.urlImage()).addDataset("alt", "imagem de capa");

        divCardBody.append(pCardType).append(imgCardImg);
        divCardFooter.append(pCardText);
        
        divCard.append(h4CardHeader).append(divCardBody).append(divCardFooter);
        
        return divCard;
    }

    private HTMLElement createHead(){
        HTMLElement head = new HTMLElement("head");
        HTMLElement title = new HTMLElement("title");
        HTMLElement metaCharset = new HTMLElement("meta", false);
        HTMLElement metaViewport = new HTMLElement("meta", false);
        HTMLElement linkCSSBootStrap = new HTMLElement("link", false);

        metaCharset.addDataset("charset", "utf-8");
        
        metaViewport.name = "viewport";
        metaViewport.addDataset("content", "width=device-width, initial-scale=1, shrink-to-fit=no");

        linkCSSBootStrap.addDataset("rel", "stylesheet");
        linkCSSBootStrap.addDataset("href", "https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css");
        linkCSSBootStrap.addDataset("integrity", "sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm");
        linkCSSBootStrap.addDataset("crossorigin", "anonymous");

        title.textContent = "Filmes";
       
        
        head.append(metaCharset);
        head.append(metaViewport);
        head.append(linkCSSBootStrap);
        head.append(title); 

        return head;

    }
    
    private HTMLElement createPage(){
        HTMLElement html = new HTMLElement("html");        
        
        HTMLElement body = new HTMLElement("body");
        
        html.append(createHead());        
        html.append(body);

        return html;
    }

    public void generate(List<Content> contents){
        HTMLElement page = createPage();
        HTMLElement body = page.getChild(1);
        HTMLElement divRow = new HTMLElement("div", "row");
        
        contents.forEach(content -> {
            divRow.append(createCard(content));
        });

        body.append(divRow);
        String saida = page.generated();

        try {
            writer.write(saida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
       this.writer.close();    
    }

    
}
