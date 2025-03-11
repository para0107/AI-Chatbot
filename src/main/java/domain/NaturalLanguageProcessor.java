package domain;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NaturalLanguageProcessor {
private SentenceDetectorME sentenceDetector;
private TokenizerME tokenizer;
private NameFinderME nameFinder;

public NaturalLanguageProcessor()
{
    try{
        //Sentence detection model
        InputStream sentenceModelStream = new FileInputStream("models/en-sent.bin");
        SentenceModel sentenceModel = new SentenceModel(sentenceModelStream);
        sentenceDetector = new SentenceDetectorME(sentenceModel);

        InputStream tokenModelStream = new FileInputStream("models/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(tokenModelStream);
        tokenizer = new TokenizerME(tokenModel);

        // Load Named Entity Recognition (NER) model
        InputStream nerModelStream = new FileInputStream("models/en-ner-person.bin");
        TokenNameFinderModel nerModel = new TokenNameFinderModel(nerModelStream);
        nameFinder = new NameFinderME(nerModel);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

   public String[] detectSentences(String text)
   {
       return sentenceDetector.sentDetect(text);
   }
   public String[] tokenizetext(String text)
   {
       return tokenizer.tokenize(text);
   }
   public String[] findNames(String text)
   {
       String[] tokens = tokenizer.tokenize(text);
       Span[] nameSpans = nameFinder.find(tokens);
       String[] names = new String[nameSpans.length];
       for(int i = 0; i < nameSpans.length; i++)
       {
           names[i] = tokens[nameSpans[i].getStart()];
       }
       return names;
   }
}

