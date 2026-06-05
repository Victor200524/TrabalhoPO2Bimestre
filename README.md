# 🌳 Estruturas de Dados Avançadas: Árvore PATRICIA e Árvore B+

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)
![Unoeste](https://img.shields.io/badge/FIPP%20·%20Unoeste-7º%20Termo-purple?style=for-the-badge)

Implementações *from scratch* de duas árvores de busca avançadas, desenvolvidas como requisito prático do 7º Termo de Ciência da Computação na **FIPP – Unoeste**.

> **Restrição principal:** uso de qualquer estrutura nativa da linguagem (`ArrayList`, `java.util.Stack`, `java.util.Queue`, etc.) foi estritamente proibido. Todas as estruturas de apoio — pilhas e filas — foram implementadas manualmente.

---

## 📂 Árvore PATRICIA

> *Practical Algorithm To Retrieve Information Coded In Alphanumeric*

Trie compactada onde caminhos com nós de filho único são agrupados, otimizando espaço e prefixos.

### Funcionalidades

| # | Funcionalidade | Descrição |
|---|---|---|
| 1 | **Inserção compactada** | Trata colisões e quebra de nós para prefixos inseridos no meio de caminhos existentes. |
| 2 | **Programação estruturada pura** | Sem `break`, `continue` ou múltiplos `return` — totalmente iterativo. |
| 3 | **Busca iterativa** | Verificação rápida de strings na árvore. |
| 4 | **DFS com pilha customizada** | Exibição alfabética em profundidade sem recursão. |
| 5 | **BFS com fila customizada** | Exibição nível a nível com posição de divergência e letras de cada nó. |

---

## 📂 Árvore B+

Estrutura auto-balanceada para armazenamento: todos os dados residem exclusivamente nas folhas, conectadas em lista encadeada para leitura sequencial eficiente.

### Funcionalidades

| # | Funcionalidade | Descrição |
|---|---|---|
| 1 | **Ordem dinâmica** | Recebe ordem `N` no construtor; suporta ordens pares e ímpares. |
| 2 | **Inserção e split inteligente** | Diferencia split de folha (cópia) de split de nó interno (promoção). |
| 3 | **Exclusão e balanceamento** | Underflow tratado via empréstimo (redistribuição) ou fusão, mantendo ocupação ≥ 50%. |
| 4 | **Leitura sequencial** | Navega à folha mais à esquerda e varre lateralmente pela lista encadeada. |
| 5 | **Caminhamento in-ordem** | Travessia recursiva intercalando filhos e chaves. |
| 6 | **Stress test** | Inserção sequencial e exclusão massiva em árvores de ordem `N=4` e `N=5`. |

---

## 💻 Como executar

Testado em **Arch Linux (GNOME)** com JDK padrão.

**1. Clone o repositório**
```bash
git clone https://github.com/SEU-USUARIO/NOME-DO-REPOSITORIO.git
cd NOME-DO-REPOSITORIO
```

**2. Escolha a estrutura, compile e execute**
```bash
# Árvore PATRICIA
cd src/arvorePatricia && javac *.java && java Main

# Árvore B+
cd src/arvoreBmais && javac *.java && java Main
```

---

## 👨‍💻 Autor

**Victor** — 7º Termo de Ciência da Computação · Unoeste (FIPP) · Foco em desenvolvimento Java
