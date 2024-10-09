import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import axios from "axios";
import Dashboard from "./Dashboard"; // Assuming your component is in the same directory

jest.mock("axios"); // Mock axios to control API responses

test("fetches and displays job data", async () => {
  // Mock a successful API response
  const mockJobData = [
    { id: 1, companyName: "Company A", title: "Job A", skills: "Skill A" },
    { id: 2, companyName: "Company B", title: "Job B", skills: "Skill B" },
  ];
  axios.get.mockResolvedValueOnce({ data: mockJobData });

  render(<Dashboard />);

  // Check if the loading indicator is displayed initially
  expect(screen.getByText("Loading")).toBeInTheDocument();

  // Wait for the data to be fetched and displayed
  await waitFor(() => {
    expect(screen.getByText("Company A")).toBeInTheDocument();
    expect(screen.getByText("Job A")).toBeInTheDocument();
    expect(screen.getByText("Company B")).toBeInTheDocument();
    expect(screen.getByText("Job B")).toBeInTheDocument();
  });

  // Check if the loading indicator is gone
  expect(screen.queryByText("Loading")).not.toBeInTheDocument();
});
