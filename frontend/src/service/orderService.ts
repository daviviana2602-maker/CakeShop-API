import api from "../api/api"



export async function createOrder() {

  const response = await api.post("/order/create")

  return response.data

}


export interface CartRequest {
  productId: number
  quantity: number
}

export async function addItem(orderId: number, data: CartRequest) {

  const response = await api.post(
    `/order/${orderId}/items`,
    data
  )

  return response.data

}


export async function listOrder(orderId: number) {

  const response = await api.get(
    `/order/${orderId}/list`
  )

  return response.data

}


export async function cancelOrder(orderId: number) {

  const response = await api.post(
    `/order/${orderId}/cancel`
  )

  return response.data

}


export async function concludeOrder(orderId: number) {

  const response = await api.post(
    `/order/${orderId}/conclude`
  )

  return response.data

}