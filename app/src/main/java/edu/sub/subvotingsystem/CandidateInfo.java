package edu.sub.subvotingsystem;

public class CandidateInfo {
    private String candidateName;
    private String candidatePartyName;
    private String candidateGender;
    private int voteCount;


    public CandidateInfo() {
        // Default constructor required for Firebase
    }

    public CandidateInfo(String candidateName, String candidatePartyName, String candidateGender, int voteCount) {
        this.candidateName = candidateName;
        this.candidatePartyName = candidatePartyName;
        this.candidateGender = candidateGender;
        this.voteCount = voteCount;
    }

    // Getters and setters for the fields
    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidatePartyName() {
        return candidatePartyName;
    }

    public void setCandidatePartyName(String candidatePartyName) {
        this.candidatePartyName = candidatePartyName;
    }

    public String getCandidateGender() {
        return candidateGender;
    }

    public void setCandidateGender(String candidateGender) {
        this.candidateGender = candidateGender;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

}
