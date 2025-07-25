package org.grew.grewwebsiteserver.domain.post.utils

import edu.stanford.nlp.pipeline.CoreDocument
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import org.openkoreantext.processor.KoreanPosJava
import org.openkoreantext.processor.OpenKoreanTextProcessorJava
import java.util.*

object TextPreprocessor {

    private val stopWords = setOf("은", "는", "이", "가", "을", "를", "에", "의", "도", "로", "에서", "와", "과", "하고")

    private val props = Properties().apply {
        setProperty("annotators", "tokenize,ssplit,pos,lemma")
        setProperty("tokenize.language", "en")
    }

    private val pipeline = StanfordCoreNLP(props)

    /**
     * 전체 전처리 (한국어 + 영어)
     */
    fun preprocess(text: String): String {
        val koreanTokens = preprocessKorean(text)
        val englishTokens = preprocessEnglish(text)
        return (koreanTokens + englishTokens)
            .toSet() // 중복 제거
            .joinToString(" ")
    }

    /**
     * 한국어 전처리: 형태소 분석 + 어간 추출 + 불용어 제거
     */
    private fun preprocessKorean(text: String): Set<String> {
        // 1. 정규화
        val normalized = OpenKoreanTextProcessorJava.normalize(text)

        // 2. 토크나이징
        val tokens = OpenKoreanTextProcessorJava.tokenize(normalized)

        // 3. 어간 분석 (stem)
        val javaTokens = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens)

        // 4. 필터링 및 어간 추출
        return javaTokens
            .asSequence()
            .filter {
                it.pos in listOf(
                    KoreanPosJava.Noun,
                    KoreanPosJava.Verb,
                    KoreanPosJava.Adjective
                )
            }
            .map { token ->
                if (!token.stem.isNullOrBlank()) token.stem else token.text
            } // 어간 추출 가능 시 사용
            .filter { it.length > 1 && !stopWords.contains(it) } // 불용어/1글자 제거
            .toSet() // 중복 제거
    }

    /**
     * 영어 전처리: 알파벳만 추출 후 어간 추출
     */
    fun preprocessEnglish(text: String): Set<String> {
        val document = CoreDocument(text)
        pipeline.annotate(document)

        return document.tokens().mapNotNull { token ->
            val lemma = token.lemma().lowercase()
            // 필터: 길이 2 이상, 숫자/기호 제거, 예외 단어 제거
            if (lemma.length > 2 && lemma.all { it.isLetter() } && Regex("[a-zA-Z]{2,}").matches(lemma)) lemma else null
        }.toSet()
    }
}
