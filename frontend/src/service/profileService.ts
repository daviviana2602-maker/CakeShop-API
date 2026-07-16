import api from "../api/api"


export interface UpdateProfileRequest {
  name?: string
  newEmail?: string
}


export async function updateProfile(data: UpdateProfileRequest) {

  const response = await api.patch("/profile/", data)

  return response.data
}



export interface UpdatePasswordRequest {
  currentPassword: string
  newPassword: string
}


export async function updatePassword(data: UpdatePasswordRequest) {

  const response = await api.patch("/profile/password", data)

  return response.data

}


export async function deleteProfile() {

  const response = await api.delete("/profile/")

  return response.data

}