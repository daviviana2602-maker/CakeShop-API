import api from "../api/api"


export interface CreateProductRequest {
  name: string
  price: number
}

export async function createProduct(data: CreateProductRequest) {
  const response = await api.post("/product/create", data)

  return response.data
}