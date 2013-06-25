package jp.scid.bio.sequence;

/**
 * 塩基やアミノ酸といった、符号配列の情報の構造です。
 * 
 * @author HIGUCHI Ryusuke
 */
public interface SequenceBioData {
    /**
     * この情報の名前を返します。
     * 
     * @return 名前
     */
    String name();
    
    /**
     * 配列の記号文字列を返します。
     * 
     * @return 配列の文字列
     */
    String sequence();
    
    /**
     * 配列の長さを返します。
     * 
     * @return 配列の長さ
     */
    int sequenceLength();
    
    /**
     * アクセッション番号を返します。
     * 
     * @return アクセッション番号。不明のときは空の文字列。
     */
    String accessionNumber();
    
    /**
     * アクセッション番号のバージョン数を返します。
     * 
     * @return バージョン番号。不明は 0 。
     */
    int accessionVersion();
    
    /**
     * 配列の名前空間を返します。
     * @return 名前空間名。不明のときは空の文字列。
     */
    String namespace();
    
    /**
     * 配列の説明を返します。
     * 
     * @return 説明。定義名など。不明のときは空の文字列。
     */
    String description();
}
