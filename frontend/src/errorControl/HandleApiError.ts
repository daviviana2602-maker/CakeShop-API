import axios from "axios";
import { ErrorMessages } from "./ErrorMessages";
import { ErrorCodeEnum} from "./ErrorCodeEnum";


export interface ApiError {
    ErrorCode: ErrorCodeEnum;
    message: string;
}


export function handleApiError(error: unknown): string {

    if (!axios.isAxiosError(error) || !error.response) {
        return "Ocorreu um erro inesperado.";
    }

    const apiError = error.response.data as ApiError;

    return ErrorMessages[apiError.ErrorCode] ?? "Ocorreu um erro inesperado.";

}