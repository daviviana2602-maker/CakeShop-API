import { ErrorCodeEnum } from "./ErrorCodeEnum";


export const ErrorMessages: Record<ErrorCodeEnum, string> = {

    // AUTH
    [ErrorCodeEnum.INVALID_CREDENTIALS]: "Email ou senha inválidos.",
    [ErrorCodeEnum.TOKEN_EXPIRED]: "Sessão Expirada.",

    // USER
    [ErrorCodeEnum.USER_NOT_FOUND]: "Usuário não encontrado.",
    [ErrorCodeEnum.USER_DISABLED]: "Usuário está desativada.",
    [ErrorCodeEnum.USER_DELETED]: "Este usuário foi deletado.",
    [ErrorCodeEnum.USER_NOT_VERIFIED]: "Conta ainda não verificada.",
    [ErrorCodeEnum.EMAIL_ALREADY_EXISTS]: "Este e-mail já está em uso.",
    [ErrorCodeEnum.USER_NOT_ADMIN]: "Este usuário não é um administrador.",
    [ErrorCodeEnum.USER_ALREADY_ADMIN]: "Este usuário já é administrador.",
    [ErrorCodeEnum.USER_ACTIVATED]: "Usuário já está ativado.",
    [ErrorCodeEnum.WRONG_PASSWORD]: "Senha incorreta.",

    // ADMIN
    [ErrorCodeEnum.USER_ACTION_FORBIDDEN]: "Você não pode realizar essa ação.",

    // PRODUCT
    [ErrorCodeEnum.PRODUCT_NOT_FOUND]: "Produto não encontrado.",
    [ErrorCodeEnum.PRODUCT_ALREADY_EXISTS]: "Já existe um produto com esse nome.",
    [ErrorCodeEnum.PRODUCT_INVALID_PRICE]: "Preço inválido.",

    // ORDER
    [ErrorCodeEnum.ORDER_NOT_FOUND]: "Pedido não encontrado.",
    [ErrorCodeEnum.ORDER_FINISHED]: "Este pedido já foi finalizado.",

    // CART
    [ErrorCodeEnum.INVALID_QUANTITY]: "Quantidade inválida.",
    [ErrorCodeEnum.CART_EMPTY]: "Seu carrinho está vazio.",

    // DTOs
    [ErrorCodeEnum.VALIDATION_ERROR]: "Existem campos inválidos no formulário.",

    // NAMES
    [ErrorCodeEnum.INVALID_NAME]: "Caso nome, são necessários ao menos 3 caracteres.",
    [ErrorCodeEnum.NAME_ALREADY_EXISTS]: "Esse nome já está em uso.",

    // EMAIL
    [ErrorCodeEnum.INVALID_EMAIL]: "E-mail inválido.",

    // REQUEST
    [ErrorCodeEnum.ONE_FIELD_REQUIRED]: "Preencha os campos necessários.",

    // RATE LIMIT
    [ErrorCodeEnum.MANY_REQUESTS]: "Muitas requisições. Tente novamente em alguns minutos."
    
};