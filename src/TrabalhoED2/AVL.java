/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrabalhoED2;

/*+-------------------------------------------------------------+
 | UNIFAL – Universidade Federal de Alfenas. |
 | BACHARELADO EM CIENCIA DA COMPUTACAO. |
 | Atividade.: Implementação não recursiva da árvore AVL |
 | Disciplina: Estrutura de Dados II |
 | Professor.: Luiz Eduardo da Silva |
 | Aluno.....: Rafael Alves Menicucci Pinto |
 | Data......: 05/02/2018 |
 +-------------------------------------------------------------+*/

import java.util.Scanner;

public class AVL {

    boolean altera;
    boolean percorre = false;
    boolean consulta = false;
    Node raiz;

    public AVL() {
        raiz = null;
    }

    public Node insereNo(int info) {
        Node atual = raiz;
        percorre = true;
        if (raiz == null) {
            raiz = new Node(info);
            return raiz;
        }
        altera = true;
        while (percorre) {
            if (info < atual.info && atual.filho != null) {
                atual = atual.esq(atual);
            } else if (info > atual.info && atual.dir(atual) != null) {
                atual = atual.dir(atual);
            } else {
                percorre = false;
            }
        }

        Node no = new Node(info);
        if (info > atual.info) {
            if (atual.esq(atual) != null) {
                atual.filho.irmaoPai = no;
                no.irmaoPai = atual;
            } else {
                atual.filho = no;
                no.irmaoPai = atual;
            }
        } else {
            if (atual.dir(atual) != null) {
                atual.filho = no;
                no.irmaoPai = atual.dir(atual);
            } else {
                atual.filho = no;
                no.irmaoPai = atual;
            }
        }

        while (altera && atual != null) {
            if (atual.dir(atual) == no) {
                switch (atual.fb) {
                    case 1:
                        atual.fb = 0;
                        altera = false;
                        break;

                    case 0:
                        atual.fb = -1;
                        break;

                    case -1:
                        if (atual.dir(atual).fb == -1) {
                            atual = rotacaoEsquerda(atual);
                        } else {
                            atual = rotacaoDireitaEsquerda(atual);
                        }
                        atual.fb = 0;
                        altera = false;
                        break;
                }
            } else {
                switch (atual.fb) {
                    case -1:
                        atual.fb = 0;
                        altera = false;
                        break;
                    case 0:
                        atual.fb = 1;
                        break;
                    case 1:
                        if (atual.esq(atual).fb == 1) {
                            atual = rotacaoDireita(atual);
                        } else {
                            atual = rotacaoEsquerdaDireita(atual);
                        }
                        atual.fb = 0;
                        altera = false;
                        break;
                }
            }
            if (altera == false && atual.pai(atual) == null) {
                raiz = atual;
                break;
            }
            no = atual;
            atual = atual.pai(atual);
        }

        return raiz;
    }

    public boolean consulta(Node raiz, int info) {
        Node atual;

        if (raiz != null) {
            atual = raiz;
            while (atual != null) {
                if (info == atual.info) {
                    return true;
                } else {
                    if (info > atual.info) {
                        atual = atual.dir(atual);
                    } else {
                        atual = atual.esq(atual);
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public Node rotacaoDireita(Node A) {
        System.out.print("D ");

        Node E = A.esq(A);
        if (E.dir(E) == null) {
            E.filho.irmaoPai = A;
            if (A.irmaoPai != null) {
                if (A.pai(A).info > A.info) {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho = E;
                } else {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho.irmaoPai = E;
                }
            } else {
                E.irmaoPai = null;
            }
            A.irmaoPai = E;
            A.filho = null;
            A.fb = 0;
            E.fb = 0;
        } else {
            A.filho = E.dir(E);
            A.filho.irmaoPai = E.irmaoPai;
            if (A.irmaoPai != null) {
                if (A.pai(A).info > A.info) {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho = E;
                } else {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho.irmaoPai = E;
                }
            } else {
                E.irmaoPai = null;
            }
            A.irmaoPai = E;
            E.filho.irmaoPai = A;
            A.fb = 0;
            E.fb = 0;
        }

        return E;

    }

    public Node rotacaoEsquerda(Node A) {
        System.out.print("E ");
        Node E = A.dir(A);

        if (A.filho.irmaoPai == A) {
            if (A.irmaoPai != null) {
                E.irmaoPai = A.irmaoPai;
                if (A.pai(A).info > A.info) {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho = E;
                } else {
                    E.irmaoPai = A.irmaoPai;
                    A.irmaoPai.filho.irmaoPai = E;
                }
            } else {
                E.irmaoPai = null;
            }
            A.irmaoPai = E.filho;
            E.filho = A;
            A.filho = null;
            A.fb = 0;
            E.fb = 0;

        } else {
            A.filho.irmaoPai = E.filho;
            if (A.irmaoPai != null) {
                E.irmaoPai = A.irmaoPai;
                if (A.pai(A).info > A.info) {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho = E;
                } else {
                    E.irmaoPai = A.irmaoPai;
                    A.pai(A).filho.irmaoPai = E;
                }
            } else {
                E.irmaoPai = null;
            }
            A.irmaoPai = E.filho.irmaoPai;
            E.filho.irmaoPai = A;
            E.filho = A;
            A.fb = 0;
            E.fb = 0;
        }

        return E;
    }

    public Node rotacaoEsquerdaDireita(Node A) {
        System.out.print("ED ");
        Node E = A.esq(A);
        Node D = E.dir(E);

        if (A.dir(A) == null && E.esq(E) == null && D.filho == null) {
            D.filho = E;
            D.filho.irmaoPai = A;
            E.filho = null;
            A.filho = null;

            if (A.irmaoPai == null) {
                D.irmaoPai = null;
            } else {

                if (A.irmaoPai.irmaoPai == A) {
                    A.irmaoPai.filho = D;
                    D.irmaoPai = A.irmaoPai;
                } else {
                    if (A.info > A.pai(A).info) {
                        A.irmaoPai.filho.irmaoPai = D;
                        D.irmaoPai = A.irmaoPai;
                    } else {
                        A.irmaoPai.irmaoPai.filho = D;
                        D.irmaoPai = A.irmaoPai;
                    }
                }
            }
        } else {
            if (D.filho.irmaoPai == D) {
                if (D.info > D.filho.info) {
                    A.filho = E.irmaoPai;
                    E.filho.irmaoPai = D.filho;
                    E.filho.irmaoPai.irmaoPai = E;
                    D.filho = E;
                    D.filho.irmaoPai = A;
                    if (A.irmaoPai == null) {
                        D.irmaoPai = null;
                    } else {

                        if (A.irmaoPai.irmaoPai == A) {
                            A.irmaoPai.filho = D;
                            D.irmaoPai = A.irmaoPai;
                        } else {
                            if (A.info > A.pai(A).info) {
                                A.irmaoPai.filho.irmaoPai = D;
                                D.irmaoPai = A.irmaoPai;
                            } else {
                                A.irmaoPai.irmaoPai.filho = D;
                                D.irmaoPai = A.irmaoPai;
                            }
                        }
                    }
                } else {
                    A.filho = D.filho;
                    A.filho.irmaoPai = E.irmaoPai;
                    E.filho.irmaoPai = E;
                    D.filho = E;
                    D.filho.irmaoPai = A;
                    if (A.irmaoPai == null) {
                        D.irmaoPai = null;
                    } else {

                        if (A.irmaoPai.irmaoPai == A) {
                            A.irmaoPai.filho = D;
                            D.irmaoPai = A.irmaoPai;
                        } else {
                            if (A.info > A.pai(A).info) {
                                A.irmaoPai.filho.irmaoPai = D;
                                D.irmaoPai = A.irmaoPai;
                            } else {
                                A.irmaoPai.irmaoPai.filho = D;
                                D.irmaoPai = A.irmaoPai;
                            }
                        }
                    }
                }
            } else {
                E.filho.irmaoPai = D.filho;
                A.filho = D.filho.irmaoPai;
                D.filho = E;
                A.filho.irmaoPai = E.irmaoPai;
                D.filho.irmaoPai = A;
                E.filho.irmaoPai.irmaoPai = E;
                if (A.irmaoPai == null) {
                    D.irmaoPai = null;
                } else {

                    if (A.irmaoPai.irmaoPai == A) {
                        A.irmaoPai.filho = D;
                        D.irmaoPai = A.irmaoPai;
                    } else {
                        if (A.info > A.pai(A).info) {
                            A.irmaoPai.filho.irmaoPai = D;
                            D.irmaoPai = A.irmaoPai;
                        } else {
                            A.irmaoPai.irmaoPai.filho = D;
                            D.irmaoPai = A.irmaoPai;
                        }
                    }
                }
            }

        }

        A.irmaoPai = D;
        A.fb = (D.fb == 1) ? -1 : 0;
        E.fb = (D.fb == -1) ? 1 : 0;
        D.fb = 0;
        return D;
    }

    public Node rotacaoDireitaEsquerda(Node A) {
        System.out.print("DE ");
        Node D = A.dir(A);
        Node E = D.esq(D);

        if (A.esq(A) == null && D.dir(E) == null && E.filho == null) {
            E.filho = A;
            D.filho = null;
            A.filho = null;
            D.irmaoPai = E;

            if (A.irmaoPai == null) {
                E.irmaoPai = null;
            } else {
                if (A.irmaoPai.irmaoPai == A) {
                    A.irmaoPai.filho = E;
                    E.irmaoPai = A.irmaoPai;
                } else {
                    if (A.info > A.pai(A).info) {
                        A.irmaoPai.filho.irmaoPai = E;
                        E.irmaoPai = A.irmaoPai;
                    } else {
                        A.pai(A).filho = E;
                        E.irmaoPai = A.irmaoPai;
                    }
                }
            }
        } else {
            if (E.filho.irmaoPai == E) {
                if (E.info > E.filho.info) {
                    A.filho.irmaoPai = E.filho;
                    A.filho.irmaoPai.irmaoPai = A;
                    D.filho = E.irmaoPai;
                    E.filho = A;
                    D.irmaoPai = E;

                    if (A.irmaoPai == null) {
                        E.irmaoPai = null;
                    } else {
                        if (A.irmaoPai.irmaoPai == A) {
                            A.irmaoPai.filho = E;
                            E.irmaoPai = A.irmaoPai;
                        } else {
                            if (A.info > A.pai(A).info) {
                                A.irmaoPai.filho.irmaoPai = E;
                                E.irmaoPai = A.irmaoPai;
                            } else {
                                A.pai(A).filho = E;
                                E.irmaoPai = A.irmaoPai;
                            }
                        }
                    }

                } else {
                    D.filho = E.filho;
                    D.filho.irmaoPai = E.irmaoPai;
                    A.filho.irmaoPai = A;
                    E.filho = A;
                    D.irmaoPai = E;

                    if (A.irmaoPai == null) {
                        E.irmaoPai = null;
                    } else {
                        if (A.irmaoPai.irmaoPai == A) {
                            A.irmaoPai.filho = E;
                            E.irmaoPai = A.irmaoPai;
                        } else {
                            if (A.info > A.pai(A).info) {
                                A.irmaoPai.filho.irmaoPai = E;
                                E.irmaoPai = A.irmaoPai;
                            } else {
                                A.pai(A).filho = E;
                                E.irmaoPai = A.irmaoPai;
                            }
                        }
                    }
                }
            } else {
                D.filho = E.filho.irmaoPai;
                D.filho.irmaoPai = E.irmaoPai;
                A.filho.irmaoPai = E.filho;
                A.filho.irmaoPai.irmaoPai = A;
                E.filho = A;
                D.irmaoPai = E;

                if (A.irmaoPai == null) {
                    E.irmaoPai = null;
                } else {
                    if (A.irmaoPai.irmaoPai == A) {
                        A.irmaoPai.filho = E;
                        E.irmaoPai = A.irmaoPai;
                    } else {
                        if (A.info > A.pai(A).info) {
                            A.irmaoPai.filho.irmaoPai = E;
                            E.irmaoPai = A.irmaoPai;
                        } else {
                            A.pai(A).filho = E;
                            E.irmaoPai = A.irmaoPai;
                        }
                    }
                }
            }

        }

        A.irmaoPai = D;
        A.fb = (E.fb == -1) ? 1 : 0;
        D.fb = (E.fb == 1) ? -1 : 0;
        E.fb = 0;
        return E;
    }

    public void mostraPreOrdem(Node n) {
        if (n != null) {
            System.out.print(n.info + " ");
            mostraPreOrdem(n.esq(n));
            mostraPreOrdem(n.dir(n));
        }
    }

    public void mostraPosOrdem(Node n) {
        if (n != null) {
            mostraPosOrdem(n.esq(n));
            mostraPosOrdem(n.dir(n));
            System.out.print(n.info + " ");
        }
    }

    public void mostraEmOrdem(Node n) {
        if (n != null) {
            mostraEmOrdem(n.esq(n));
            System.out.print(n.info + " ");
            mostraEmOrdem(n.dir(n));
        }
    }

    public void mostra(Node n, int nivel) {
        if (n != null) {
            mostra(n.dir(n), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("  ");
            }
            System.out.println(n.info + " " + n.fb);
            mostra(n.esq(n), nivel + 1);
        }
    }

    public void mostra() {
        mostra(raiz, 0);
    }

    public static void main(String[] args) {
        AVL b = new AVL();

//        Scanner snum = new Scanner(System.in);
//
//        int num = -1;
//        while (num != 0) {
//            System.out.println("Digite um número: ");
//            num = snum.nextInt();
//            b.insereNo(num);
//        }

        b.insereNo(10);
        b.insereNo(4);
        b.insereNo(8);
        b.insereNo(2);
        b.insereNo(3);
        b.insereNo(1);
        b.insereNo(12);
        b.insereNo(20);
        b.insereNo(34);
        b.insereNo(6);
        b.insereNo(5);
        b.insereNo(7);
        b.insereNo(9);
        System.out.println("");
        b.mostraPreOrdem(b.raiz);
        System.out.println("");
        b.mostraEmOrdem(b.raiz);
        System.out.println("");
        b.mostraPosOrdem(b.raiz);
//ED ED D E E DE DE
//8 3 2 1 5 4 6 7 12 10 9 20 34
//1 2 3 4 5 6 7 8 9 10 12 20 34
//1 2 4 7 6 5 3 9 10 34 20 12 8

    }

}
