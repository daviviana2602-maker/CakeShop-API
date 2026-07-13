import api from "../api/api"


export interface CreateProductRequest {
  name: string
  price: number
}

export async function createProduct(data: CreateProductRequest) {
  const response = await api.post("/product/create", data)

  return response.data
}



export interface EditProductRequest {
  name: string
  price: number
}

export async function editProduct(id: number, data: EditProductRequest) {
  const response = await api.patch(`/product/${id}`, data)

  return response.data
}



export async function listProducts(page: number = 0) {
  const response = await api.get("/product/list", {
    params: {
      page
    }
  })

  return response.data
}


export async function deleteProduct(id: number) {
  const response = await api.delete(`/product/${id}`)

  return response.data
}