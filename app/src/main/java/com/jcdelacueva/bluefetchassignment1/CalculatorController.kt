package com.jcdelacueva.bluefetchassignment1

import kotlin.math.ceil
import kotlin.math.floor

class CalculatorController {
    var operand1: Float? = null
    var operand2: Float? = null
    var operation: Operation = Operation.NOT_SET

    val computationHistory = mutableListOf<HistoryItem>()

    fun setOperation(leftOperand: String, operation: Operation) {
        if (this.operation != Operation.NOT_SET) return

        operand1 = if (leftOperand.isNullOrBlank()) {
            0f
        } else {
            leftOperand.toFloatOrNull() ?: throw IllegalStateException("Invalid number")
        }

        this.operation = operation
    }

    fun clearEquation() {
        operand1 = null
        operand2 = null
        operation = Operation.NOT_SET
    }

    fun evaluate(rightOperand: String): Float? {
        if (operation == Operation.NOT_SET) return null

        operand2 = if (rightOperand.isNullOrBlank()) {
            0f
        } else {
            rightOperand.toFloatOrNull() ?: throw IllegalStateException("Invalid number")
        }

        if (operand1 == null) return null

        val result = when (operation) {
            Operation.ADD -> {
                operand1!! + operand2!!
            }
            Operation.SUB -> {
                operand1!! - operand2!!
            }
            Operation.MUL -> {
                operand1!! * operand2!!
            }
            Operation.DIV -> {
                operand1!! / operand2!!
            }
            else -> {
                null
            }
        }

        result?.let {
            computationHistory.add(
                0,
                HistoryItem(
                    getStringValueForDisplay(operand1!!),
                    getStringValueForDisplay(operand2!!),
                    getOperationSymbol(operation),
                    getStringValueForDisplay(result)
                )
            )
        }

        operation = Operation.NOT_SET
        operand2 = null

        return result
    }

    private fun getStringValueForDisplay(value: Float) =
        if (ceil(value) == floor(value)) {
            value.toInt().toString()
        } else {
            value.toString()
        }

    private fun getOperationSymbol(operation: Operation): String {
        return when (operation) {
            Operation.ADD -> {
                "+"
            }
            Operation.SUB -> {
                "-"
            }
            Operation.MUL -> {
                "x"
            }
            Operation.DIV -> {
                "/"
            }
            else -> {
                ""
            }
        }
    }

    companion object {
        enum class Operation {
            ADD,
            SUB,
            MUL,
            DIV,
            NOT_SET
        }
    }
}