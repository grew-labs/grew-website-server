package org.grew.grewwebsiteserver.domain.post.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TextPreprocessorTest {

    @Test
    fun `불용어와 특수문자가 제거되고 형태소 기준으로 정제된다`() {
        // given
        val input = "나는 오늘 사랑하는 사람과 함께 산책을 했다."

        // when
        val result = TextPreprocessor.preprocess(input)

        // then
        val tokens = result.split(" ").toSet()

        println(tokens)

        // 기대되는 주요 키워드
        assertThat(tokens).contains("오늘", "사랑", "사람과", "산책", "하다")

        // 제거되어야 할 불용어 확인
        assertThat(tokens).doesNotContain("나는", "과", "을", "를")
    }

    @Test
    fun `중복된 단어는 한 번만 포함되어야 한다`() {
        // given
        val input = "산책 산책 산책하다 산책하러 갔다."

        // when
        val result = TextPreprocessor.preprocess(input)

        println(result)

        // then
        val tokens = result.split(" ")
        assertThat(tokens).doesNotHaveDuplicates()
    }

    @Test
    fun `특수문자가 제거되어야 한다`() {
        // given
        val input = "사랑해요!!! 정말... 고마워요 :)"

        // when
        val result = TextPreprocessor.preprocess(input)

        println(result)

        // then
        assertThat(result).doesNotContain("!", ".", ":", ")")
    }

    @Test
    fun `영문 혼용 문장도 처리된다`() {
        // given
        val input = "오늘은 great day 였어요"

        // when
        val result = TextPreprocessor.preprocess(input)

        println(result)

        // then
        assertThat(result).contains("오늘", "great", "day")
    }
}
