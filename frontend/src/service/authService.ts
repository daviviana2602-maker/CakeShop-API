import api from "../api/api"


export interface CreateAccountRequest {
    name: string
    email: string
    password: string
}


export async function createAccount(data: CreateAccountRequest) {

    const response = await api.post("/auth/create", data)

    return response.data
}