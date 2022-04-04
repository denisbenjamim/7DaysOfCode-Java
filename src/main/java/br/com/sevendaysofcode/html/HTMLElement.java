package br.com.sevendaysofcode.html;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HTMLElement {
    final String TEMPLATE_CHAVE_VALOR = " {0}=\"{1}\"";
    List<String> classList = new ArrayList<>();
    List<HTMLElement> chields = new ArrayList<>(); 
    
    Map<String, Object> dataset = new LinkedHashMap<>();
    Map<String, Object> style = new LinkedHashMap<>();
    
    String id;
    String tagName;
    
    boolean fechar = true;
    
    public String name;
    public String textContent;

    public HTMLElement(String tagName) {
        this.tagName = tagName;
    }

    public HTMLElement(String tagName, boolean fechar) {
        this(tagName);
        this.fechar = fechar;
    }

    public HTMLElement(String tagName, String classInline) {
        this(tagName);
        
        if(classInline != null && !classInline.isEmpty()){
            classInline = classInline.replaceAll("\\s+", " ");//Remove espaços excessivos
            this.classList.addAll(Arrays.asList(classInline.split(" ")));
        }    
    }

    public String generated(){
        final StringBuilder ELEMENT = new StringBuilder("<").append(tagName);
        
        if(name != null){
            ELEMENT.append(format(TEMPLATE_CHAVE_VALOR, "name", name));
        }

        if(!classList.isEmpty()){
            final String CLASSES_INLINE = classList.toString().replaceAll("[^\\w\\-\\s]","");
            ELEMENT.append(format(TEMPLATE_CHAVE_VALOR, "class", CLASSES_INLINE));
        }
        
        for(Map.Entry<String, Object> entry: dataset.entrySet()){
            ELEMENT.append(format(TEMPLATE_CHAVE_VALOR, entry.getKey(), entry.getValue()));
        }

        ELEMENT.append(">");

        if(textContent != null){
            ELEMENT.append("\n\t").append(textContent);
        }
        
        //Adiciona os elementos filhos na ordem que foram inseridos
        chields.forEach(chield -> {
            ELEMENT.append("\n").append(chield.generated());
        });

        if(fechar){
            ELEMENT.append("\n</").append(tagName).append(">");
        }
        
        return ELEMENT.toString();
    }

    public void append(HTMLElement htmlElement){
        this.chields.add(htmlElement);
    }

    public HTMLElement getChield(int index){
        return this.chields.get(index);
    }

    public void addDataset(String nomePropriedade, Object valorPropriedade){
        this.dataset.put(nomePropriedade, valorPropriedade);
    }
    
    public void addClass(String nomeClasse){
        this.classList.add(nomeClasse);
    }

    public static String format(String template, Object... argumentos){
        return MessageFormat.format(template, argumentos);
    }
}
