function [out1,out] = imageCheck(funcName,varargin)
% CHECKIMAGE checks if the image outputs are the same
%   
%   Syntax:
%       log = checkImage(functionName,inputs)
%       [log, feedback] = checkImage(functionName,inputs)
%
%   The functionName is a string that is the name of the function you are
%   attempting to check, the inputs are the inputs to the function as they
%   would appear.
%
%   Example:
%       imageFunction(in1,{1,2,3},in3)
%                   becomes
%       checkImage('imageFunction',in1,{1,2,3},in3)
    
    out1 = false;
    
    % Check if the function has been run with inputs
    if isempty(varargin)
        out = 'Not enough Inputs';
        return
    end
    
    % Check if the function exists
    if exist(funcName,'file')
        fhStudent = str2func(funcName);
    else
        out = 'Function does not Exist';
        return
    end
    
    % Check if the solution exists
    if exist([funcName '_soln'],'file')
        fhSoln = str2func([funcName '_soln']);
    else
        out = 'Solution Function does not Exist';
        return
    end
    exts = imformats; exts = [exts.ext];
    
    % Get names of relavent functions and images
    filesToCopy = {[funcName '.m'],[funcName '_soln.p']};
    for i = 1:length(varargin)
        if ischar(varargin{i}) && contains(varargin{i},exts)
            % add Images to the temp dir
            if exist(varargin{i},'file')
                filesToCopy(end+1) = varargin(i); %#ok<AGROW>
            else
                out = 'One of your files does not exist';
                return
            end
        end
    end
    
    % Create new dir to run function
    cur = pwd;
    temp = tempdir();
    mkdir(fullfile(temp,'ImageTemp'));
    
    % Copy files into work dir
    cd(fullfile(temp,'ImageTemp'));
    cellfun(@(x) copyfile(fullfile(cur,x),fullfile(temp,'ImageTemp')),filesToCopy);
    
    % Get the names of all files in dir
    before = dir;
    beforeNames = {before.name};
    
    % Run Student function
    error = false;
    try
        fhStudent(varargin{:});
    catch ME
        error = true;
    end
    
    if error
        out = 'Your function errored';
        disp(ME.message)
    else
        % Get the names of all files in dir
        after1 = dir;
        after1Names = {after1.name};

        % Run Solution code
        fhSoln(varargin{:});

        % Get the names of all files in dir
        after2 = dir;
        after2Names = {after2.name};

        % Get the files output by each function
        studentFiles = setdiff(after1Names,beforeNames);
        solnFiles = setdiff(after2Names,after1Names);

        % Ensure Names are correct
        if isempty(solnFiles) || ~isequal(studentFiles,strrep(solnFiles,'_soln',''))
            out = 'Files are named incorrectly';
        else
            % Account for more than one output
            logs = false(1,length(solnFiles));
            sizeError = false;
            for i = 1:length(solnFiles)
                % Read first Image. Since names are gaurenteed to be the
                % same the order should be the same.
                soln = imread(solnFiles{i});
                student = imread(studentFiles{i});
                if isequal(size(soln),size(student))
                    mask = any(soln ~= student,3);
                    
                    % If any pixels are different produce an image with
                    % cyan as the incorrect pixels.
                    if any(any(mask))
                        figure();
                        soln = uint8(double(soln).^.75);
                        r = soln(:,:,1); g = soln(:,:,2); b = soln(:,:,3);
                        r(mask) = 0; g(mask) = 255; b(mask) = 255;
                        imshow(cat(3,r,g,b));
                    else
                        logs(i) = true;
                    end
                else
                    sizeError = true;
                end
            end
            if sizeError
                out = 'Images are different Sizes';
            elseif all(logs)
                out1 = true;
                out = 'Image is Correct';
            else
                out = 'Image is has incorrect RGB values at the CYAN pixels';
            end
        end
    end
    
    % Remove the temp folder
    cd(cur);
    delete(fullfile(temp,'ImageTemp\*.*'));
    rmdir(fullfile(temp,'ImageTemp'));
end