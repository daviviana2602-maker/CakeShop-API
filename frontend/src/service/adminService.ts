import api from "../api/api";


export interface UserResponse {

  id: number;
  name: string;
  email: string;
  role: string;
  status: string;
  verified: boolean;
  createdAt: string;

}


export async function searchUser(identifier: string) {

  const response = await api.get("/admin/search", {
    params: {
      identifier
    }
  });


  return response.data as UserResponse;

}


export async function disableUser(userId: number) {

  const response = await api.post(
    `/admin/${userId}/disable`
  );

  return response.data;

}


export async function reactivateUser(userId: number) {

  const response = await api.post(
    `/admin/${userId}/reactivate`
  );

  return response.data;

}


export async function promoteUser(userId: number) {

  const response = await api.post(
    `/admin/${userId}/promote`
  );

  return response.data;

}


export async function demoteUser(userId: number) {

  const response = await api.post(
    `/admin/${userId}/demote`
  );

  return response.data;

}