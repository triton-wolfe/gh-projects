classdef atom
    properties
        Type char
        Hybridization char {mustBeMember(Hybridization,{'s','sp1','sp2','sp3'})} = 'sp3';
        NumConnections = 0;
        Location (1,3) double {mustBeReal, mustBeFinite} = [0 0 0];
        Chirality 
        isCenter (1,1) logical = false;
        Rotation (1,2) double {mustBeReal, mustBeFinite} = [0 0]; %theta, phi
    end
    methods
        
    end
end