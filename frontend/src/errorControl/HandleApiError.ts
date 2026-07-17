import axios from "axios";
import { ErrorMessages } from "./ErrorMessages";
import { ErrorCodeEnum} from "./ErrorCodeEnum";


export interface ApiError {
    errorCode: ErrorCodeEnum;
    message: string;
}


export function handleApiError(error: unknown): string {

    console.log("ERRO RECEBIDO:", error);
    console.log("É AXIOS ERROR?", axios.isAxiosError(error));

    if (axios.isAxiosError(error)) {
        console.log("RESPONSE:", error.response);
    }

    if (!axios.isAxiosError(error) || !error.response) {
        return "Ocorreu um erro inesperado.";
    }

    const apiError = error.response.data as ApiError;

    return ErrorMessages[apiError.errorCode] ?? "Ocorreu um erro inesperado.";

}