class Day7 {
    fun main() {
        val input = java.io.File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day7.txt").readText()
        val fs = input.constructFilesystem()
        fs.computeSize()

        val part1 = fs.dfs { it is Directory && it.size < 100000 }.sumOf { it.size }
        println(part1)

        val part2 = fs.dfs { it is Directory && it.size > 30000000 - 70000000 + fs.size }.minByOrNull { it.size }!!.size
        println(part2)
    }
}

fun String.constructFilesystem(): Directory {
    val lines = split("\n")

    val fs = Directory("/")
    var cursor = fs
    var i = 0

    while (i < lines.size) {
        var line = lines[i]
        if (line.isCommand()) {
            when (val terminalCommand = line.parseTerminalCommand()) {
                TerminalCommand.LS -> {
                    i += 1
                    line = lines[i]
                    while (!line.isCommand()) {
                        if (line.startsWith("dir")) { // add a directory to current directory
                            val directoryName = line.split(" ")[1]
                            if (!cursor.children.containsKey(directoryName)) { // don't overwrite existing directory
                                val directory = Directory(directoryName)
                                directory.parent = cursor
                                cursor.children[directoryName] = directory
                            }
                        } else { // add a file to current directory
                            val (fileSize, fileName) = line.split(" ")
                            val file = File(fileName, fileSize.toInt())
                            cursor.children[fileName] = file
                        }
                        i += 1
                        if (i >= lines.size) return fs
                        line = lines[i]
                    }
                }
                TerminalCommand.CD_OUT -> {
                    cursor = cursor.parent!!
                    i += 1
                }
                TerminalCommand.CD_ROOT -> {
                    cursor = fs
                    i += 1
                }
                is TerminalCommand.CD_IN -> {
                    val argument = terminalCommand.argument
                    cursor = cursor.children.getOrDefault(argument, Directory(argument).also { it.parent = cursor }) as Directory // assume unique namespace across directories and files
                    i += 1
                }
            }
        }
    }

    return fs
}

open class Node(val name: String, var size: Int)

class Directory(name: String) : Node(name, 0) {
    var parent: Directory? = null
    val children: MutableMap<String, Node> = mutableMapOf()
}

class File(name: String, size: Int): Node(name, size)

fun String.isCommand() = startsWith("$")

fun String.parseTerminalCommand(): TerminalCommand {
    val tokens = split(" ")
    val command = tokens[1]
    return when (command) {
        "ls" -> TerminalCommand.LS
        "cd" -> {
            val argument = tokens[2]
            return when (argument) {
                ".." -> TerminalCommand.CD_OUT
                "/" -> TerminalCommand.CD_ROOT
                else -> TerminalCommand.CD_IN(argument)
            }
        }
        else -> throw Exception("unrecognized terminal command")
    }
}

sealed class TerminalCommand {
    object LS : TerminalCommand()
    sealed class CD : TerminalCommand()
    object CD_OUT : CD()
    object CD_ROOT : CD()
    data class CD_IN(val argument: String): CD()
}

fun Directory.computeSize() {
    this.size = children.values.sumOf {
        when (it) {
            is Directory -> {
                it.computeSize()
                it.size
            }
            is File -> it.size
            else -> throw Exception("can only compute size for directories and files")
        }
    }
}

fun Node.dfs(predicate: (Node) -> Boolean): MutableList<Node> {
    val nodes = mutableListOf<Node>()

    if (predicate(this)) {
        nodes.add(this)
    }

    if (this is Directory) {
        children.values.forEach { nodes.addAll(it.dfs(predicate)) }
    }

    return nodes
}
