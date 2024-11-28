#include "ConnectFour.h"
#include <iostream>

int currentPlayer = 0;
const int ROWS = 4;
const int COLS = 5;
int board[ROWS][COLS];
bool isRedTurn = true;

JNIEXPORT void JNICALL Java_ConnectFour_startNew
(JNIEnv* env, jobject obj){
    //std::cout<<"Start planszy\n";
    for (int row = 0; row < ROWS; ++row) {
        for (int col = 0; col < COLS; ++col) {
            board[row][col] = 0;
        }
    }
}

JNIEXPORT jboolean JNICALL Java_ConnectFour_isRedTurn
        (JNIEnv* env, jobject obj){
    return isRedTurn ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_ConnectFour_changeTurn
        (JNIEnv* env, jobject obj) {
    isRedTurn = ! isRedTurn;
}


JNIEXPORT jintArray JNICALL Java_ConnectFour_getBoard(JNIEnv* env, jobject obj) {

    jintArray resultArray = env->NewIntArray(ROWS * COLS);
    if (resultArray == nullptr) {
        return nullptr;
    }

    jint flattenedBoard[ROWS * COLS];
    for (int i = 0; i < ROWS; ++i) {
        for (int j = 0; j < COLS; ++j) {
        flattenedBoard[i * COLS + j] = board[i][j];
        std::cout << '[' <<board[i][j] <<']';
        }
        std::cout << '\n';
    }
    std::cout << '\n';

    env->SetIntArrayRegion(resultArray, 0, ROWS * COLS, flattenedBoard);
    return resultArray;
}

JNIEXPORT void JNICALL Java_ConnectFour_setBoardCell(JNIEnv* env, jobject obj, jint row, jint col, jint value) {
    //std::cout<<"Java_GameLogic_setBoardCell";
    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
        board[row][col] = value;
    }
}

JNIEXPORT jboolean JNICALL Java_ConnectFour_checkWin(JNIEnv* env, jobject obj, jint color) {
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
// Poziomo:
            if (col <= COLS - 4 &&
                board[row][col] == color &&
                board[row][col + 1] == color &&
                board[row][col + 2] == color &&
                board[row][col + 3] == color) {
                return JNI_TRUE;
            }
// Pionowo:
            if (row <= ROWS - 4 &&
                board[row][col] == color &&
                board[row + 1][col] == color &&
                board[row + 2][col] == color &&
                board[row + 3][col] == color) {
                return JNI_TRUE;
            }
// Pod kątem (top lewo -> dół prawo)
            if (row <= ROWS - 4 && col <= COLS - 4 &&
                board[row][col] == color &&
                board[row + 1][col + 1] == color &&
                board[row + 2][col + 2] == color &&
                board[row + 3][col + 3] == color) {
                return JNI_TRUE;
            }
// Pod kątem (dół lewo -> top prawo)
            if (row >= 3 && col <= COLS - 4 &&
                board[row][col] == color &&
                board[row - 1][col + 1] == color &&
                board[row - 2][col + 2] == color &&
                board[row - 3][col + 3] == color) {
                return JNI_TRUE;
            }
        }
    }
    return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_ConnectFour_checkDraw
        (JNIEnv* env, jobject obj){
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
}


