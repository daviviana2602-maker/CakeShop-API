import { useToast } from "vue-toastification";
import { handleApiError } from "@/errorControl/HandleApiError";

const toast = useToast();


export function showError(error: unknown) {
    toast.error(handleApiError(error));
}


export function showSuccess(message: string) {
    toast.success(message);
}


export function showWarning(message: string) {
    toast.warning(message);
}