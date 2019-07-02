package org.camunda.feel.interpreter

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.camunda.feel._

/**
  * @author Philipp Ossler
  */
class InterpreterLiteralExpressionTest
    extends FlatSpec
    with Matchers
    with FeelIntegrationTest {

  "A literal" should "be a number" in {

    eval("2") should be(ValNumber(2))
    eval("2.4") should be(ValNumber(2.4))
    eval("-3") should be(ValNumber(-3))
  }

  it should "be a string" in {

    eval(""" "a" """) should be(ValString("a"))
  }

  it should "be a boolean" in {

    eval("true") should be(ValBoolean(true))
  }

  it should "be null" in {

    eval("null") should be(ValNull)
  }

  it should "be a context" in {

    eval("{ a : 1 }") should be(
      ValContext(DefaultContext(Map("a" -> ValNumber(1)))))

    eval("""{ a:1, b:"foo" }""") should be(
      ValContext(
        DefaultContext(Map("a" -> ValNumber(1), "b" -> ValString("foo")))))

    // nested
    eval("{ a : { b : 1 } }") should be(
      ValContext(DefaultContext(
        Map("a" -> ValContext(DefaultContext(Map("b" -> ValNumber(1))))))))

  }

  it should "be a list" in {

    eval("[1]") should be(ValList(List(ValNumber(1))))

    eval("[1,2]") should be(ValList(List(ValNumber(1), ValNumber(2))))

    // nested
    eval("[ [1], [2] ]") should be(
      ValList(List(ValList(List(ValNumber(1))), ValList(List(ValNumber(2))))))
  }

}
