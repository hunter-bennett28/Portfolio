/*! \file: fileusage_main.cpp
	\author Hunter Bennett
	\date 2019-04-15
	\version 1.0.0
	\brief Implementation of a file system scanner that reports the existence,
		   occurance, and total size of given file types from given starting 
		   path.	
*/

#include <iostream>
#include <iomanip>
#include <locale>
#include <regex>
#include <map>
#include <string>
#include <filesystem>

using namespace std;
using namespace std::experimental::filesystem;


/*! \fn void scan_recursive(path const& file, map<string, pair<size_t, uintmax_t>>& extensions)
	\brief Scans filesystem from given starting path recursively and adds each file to a
		   map by its extension, keeps count of total files found of each extension and their
		   cumulative size in bytes. Can throw filesystem_errors but catches them.
	\param file [in] file path to be read from.
		   extensions [in] map that stores extensions, their occurance and cumulative size.
	\return Nothing. 
*/
void scan_recursive(path const& file, map<string, pair<size_t, uintmax_t>>& extensions) {
	recursive_directory_iterator current(file);
	recursive_directory_iterator end;
	for (auto& f = current; f != end; ++f) {
		//only saving data for files, not folders
		if (!is_directory(f->status())) {
			//getting extension of each file
			string ext = current->path().extension().string();
			++extensions[ext].first;
			extensions[ext].second += file_size(current->path());
		}
	}
}

/*! \fn void scan_folder(path const& file, map<string, pair<size_t, uintmax_t>>& extensions)
	\brief Scans files in given path and adds each file to a map by its extension, keeps count 
		   of total files found of each extension and their cumulative size in bytes. Can throw 
		   filesystem_errors but catches them.
	\param file [in] file path to be read from.
		   extensions [in] map that stores extensions, their occurance and cumulative size.
	\return Nothing.
*/
void scan_folder(path const& file, map<string, pair<size_t, uintmax_t>>& extensions) {
	directory_iterator current(file);
	directory_iterator end;
	for (auto& f = current; f != end; ++f) {
		if (!is_directory(f->status())) {
			string ext = current->path().extension().string();
			++extensions[ext].first;
			extensions[ext].second += file_size(current->path());
		}
	}
}

/*! \fn size_t longest_ext(map<string, pair<size_t, uintmax_t>> const& extensions,
		bool printEmpty)
	\brief Finds the longest key in given map, either in general or of 
		   the keys with non-zero values.
	\param extensions [in] map that holds extensions, their occurance and cumulative size.
		   includeEmpty [in] a boolean that decides whether to count keys with values of zero.
	\return A size_t that holds the length of the longest considered key plus 1 for padding,
			with a minumum of 4.
*/
size_t longest_ext(map<string, pair<size_t, uintmax_t>> const& extensions,
	bool includeEmpty) {
	size_t longest = 3; //starting at 3 to fit at least 'Ext'
	for (auto& i : extensions) {
		//saving longest key
		if (i.first.length() > longest && (i.second.first != 0 || includeEmpty)) {
			longest = i.first.length();
		}
	}
	return longest + 1;
}

/*! \fn size_t longest_ext(vector <pair<string, pair<size_t, uintmax_t>>> const& extensions, 
		bool printEmpty)
	\brief An override of above function using a vector instead of a map.
	\param extensions [in] vector that holds extensions, their occurance and cumulative size.
		   includeEmpty [in] a boolean that decides whether to count keys with values of zero.
	\return A size_t that holds the length of the longest considered key plus 1 for padding,
			with a minumum of 4.
*/
size_t longest_ext(vector <pair<string, pair<size_t, uintmax_t>>> const& extensions, 
	bool includeEmpty) {
	size_t longest = 3; //starting at 3 to fit at least 'Ext'
	for (auto& i : extensions) {
		if (i.first.length() > longest && (i.second.first != 0 || includeEmpty)) {
			longest = i.first.length();
		}
	}
	return longest + 1;
}


/*! \fn size_t space_needed(uintmax_t num)
	\brief Finds total screen space needed to hold a given value including comma separators,
		   and returns a minimum of 2 for formatting.
	\param num [in] the largest value that must fit on the screen.
	\return A size_t that holds the screen space required to fit the given number.
*/
size_t space_needed(uintmax_t num) {
	constexpr size_t MINIMUM_WIDTH = 2; //minimum desired width
	if (num == 0)
		return MINIMUM_WIDTH; 
	//getting total number of digits plus padding
	size_t digits = (size_t)floor(log10((double)num)) + MINIMUM_WIDTH;

	//adding space for comma separator
	while (num /= 1000) {
		++digits;
	}
	return digits;
}

/*! \fn bool sort_by_size(pair<string, pair<size_t, uintmax_t>> const& lhs,
		pair<string, pair<size_t, uintmax_t>> const& rhs)
	\brief A comparison function for deciding which element of a vector is
		   greater.
	\param lhs [in] the first element.
		   rhs [in] the second element.
	\return A bool stating if the first element is less than the second.
*/
bool sort_by_size(pair<string, pair<size_t, uintmax_t>> const& lhs,
	pair<string, pair<size_t, uintmax_t>> const& rhs) {
	//comparing the total file sizes
	return lhs.second.second < rhs.second.second;
}

/*! \fn void print_map_by_size(map<string, pair<size_t, uintmax_t>>& extensions, bool reverse, 
		regex userExts, size_t& totalFiles, uintmax_t& totalSize, bool printEmpty)
	\brief Prints a given map with three-column formatting in order based on the size of 
		   values. This function sacrifices ease of use for functionality.
	\param extensions [in] the map to be printed containing extensions, their occurance and
		   cummulative size.
		   reverse [in] a bool representing whether to print map in reverse.
		   userExts [in] a regex used to determine which extensions to sort and report.
		   totalFiles [in] the total number of files matching the regex.
		   totalSize [in] the total size in bytes of files matching the desired extensions.
		   printEmpty [in] a bool deciding whether to print keys with zero values.
	\return Nothing.
*/
void print_map_by_size(map<string, pair<size_t, uintmax_t>>& extensions, bool reverse, regex userExts,
	size_t& totalFiles, uintmax_t& totalSize, bool printEmpty) {

	//copying map into vector for sorting
	vector <pair<string, pair<size_t, uintmax_t>>> extensionsVector;
	for (auto& i : extensions) {
		if (regex_match(i.first, userExts)) {
			//loading elements that match regular expression into vector for sorting
			extensionsVector.push_back(pair<string, pair<size_t, uintmax_t>>(
				i.first, pair<size_t, uintmax_t>(i.second.first, i.second.second)));
		}
	}

	size_t extWidth = longest_ext(extensionsVector, printEmpty);
	//resetting values
	totalFiles = 0;
	totalSize = 0;

	for (auto& i : extensionsVector) {
		totalFiles += i.second.first;
		totalSize += i.second.second;
	}

	size_t numWidth = space_needed(totalFiles);
	size_t totalWidth = space_needed(totalSize);
	size_t extensionsFound = 0;

	//including comma separators and scaling width based on content
	cout.imbue(locale(""));
	cout << "\n\n" << setw(extWidth) << "Ext" << " : " << setw(numWidth) << "#"
		<< " : " << setw(totalWidth) << "Total" << endl;
	//printing dash line separator, width is plus 3 to include ' : ' in calculation
	cout << setfill('-') << setw(extWidth + 3) << " : " << setw(numWidth + 3)
		<< " : " << setw(totalWidth) << "-" << endl;

	sort(extensionsVector.begin(), extensionsVector.end(), sort_by_size);

	if (reverse) {
		//printing in reverse
		for (int i = (int)extensionsVector.size() - 1; i >= 0; --i) {
			if (extensionsVector[i].second.first != 0 || printEmpty) {
				++extensionsFound;
				cout << setfill(' ') << setw(extWidth) << extensionsVector[i].first << " : " << setw(numWidth) <<
					extensionsVector[i].second.first << " : " << setw(totalWidth) <<
					extensionsVector[i].second.second << endl;
			}
		}
	}
	else {
		//printing in correct order
		for (size_t i = 0; i < extensionsVector.size(); ++i) {
			if (extensionsVector[i].second.first != 0 || printEmpty) {
				++extensionsFound;
				cout << setfill(' ') << setw(extWidth) << extensionsVector[i].first << " : " << setw(numWidth) <<
					extensionsVector[i].second.first << " : " << setw(totalWidth) <<
					extensionsVector[i].second.second << endl;
			}
		}
	}

	//printing bottom line formatting
	cout << setfill('-') << setw(extWidth + 3) << " : " << setw(numWidth + 3)
		<< " : " << setw(totalWidth) << "-" << endl;

	//printing totals
	cout << setfill(' ') << setw(extWidth) << extensionsFound << " : " << setw(numWidth) <<
		totalFiles << " : " << setw(totalWidth) << totalSize << endl;
}

/*! \fn void print_map(map<string, pair<size_t, uintmax_t>>& extensions, bool reverse, 
		size_t& totalFiles, uintmax_t& totalSize, bool printEmpty)
	\brief Prints a given map with three-column formatting in alphabetical order.
	\param extensions [in] the map to be printed containing extensions, their occurance and
		   cummulative size.
		   reverse [in] a bool representing whether to print map in reverse.
		   totalFiles [in] the total number of files in the map.
		   totalSize [in] the total size in bytes of files in the map.
		   printEmpty [in] a bool deciding whether to print keys with zero values.
	\return Nothing.
*/
void print_map(map<string, pair<size_t, uintmax_t>>& extensions, bool reverse, size_t& totalFiles, uintmax_t& totalSize,
	bool printEmpty) {
	size_t extWidth = longest_ext(extensions, printEmpty);
	totalFiles = 0;
	totalSize = 0;

	for (auto& i : extensions) {
		totalFiles += i.second.first;
		totalSize += i.second.second;
	}

	size_t numWidth = space_needed(totalFiles);
	size_t totalWidth = space_needed(totalSize);
	size_t extensionsFound = 0;

	//same printing process as above function
	cout.imbue(locale(""));
	cout << "\n\n" << setw(extWidth) << "Ext" << " : " << setw(numWidth) << "#"
		<< " : " << setw(totalWidth) << "Total\n";
	cout << setfill('-') << setw(extWidth + 3) << " : " << setw(numWidth + 3)
		<< " : " << setw(totalWidth) << "-"<< endl;
	if (reverse) {
		for (auto start = extensions.rbegin(); start != extensions.rend(); ++start) {
			if (start->second.first != 0 || printEmpty) {
				++extensionsFound;
				cout << setfill(' ') << setw(extWidth) << start->first << " : " << setw(numWidth) <<
					start->second.first << " : " << setw(totalWidth) << start->second.second
					<< endl;
			}
		}
	}
	else {
		for (auto& i : extensions) {
			if (i.second.first != 0 || printEmpty) {
				++extensionsFound;
				cout << setfill(' ') << setw(extWidth) << i.first << " : " << setw(numWidth) <<
					i.second.first << " : " << setw(totalWidth) << i.second.second
					<< endl;
			}
		}
	}
	cout << setfill('-') << setw(extWidth + 3) << " : " << setw(numWidth + 3)
		<< " : " << setw(totalWidth) << "-" << endl;
	cout << setfill(' ') << setw(extWidth) << extensionsFound << " : " << setw(numWidth) <<
		totalFiles << " : " << setw(totalWidth) << totalSize << endl;
}

/*! \fn void fill_map(map<string, pair<size_t, uintmax_t>>& extensions, 
		map<string, pair<size_t, uintmax_t>>& newMap, regex extsToFind)
	\brief Loads a map with elements matching given regular expression of another map.
	\param extensions [in] the map to be copied from.
		   newMap [in] the new map to be copied into.
		   extsToFind [in] a regular expression stating which elements to copy.
	\return Nothing.
*/
void fill_map(map<string, pair<size_t, uintmax_t>>& extensions, 
	map<string, pair<size_t, uintmax_t>>& newMap, regex extsToFind) {
	for (auto& i : extensions) {
		if (regex_match(i.first, extsToFind)) {
			newMap[i.first].first = i.second.first;
			newMap[i.first].second = i.second.second;
		}
	}
}

/*! \fn void get_all_paths(path const& file, map<string, map<string, size_t>>& locations)
	\brief Fills map with every path inside given starting location, the file types
		   they hold and how many of each non-recursively.
	\param file [in] the path to search.
		   locations [in] the map to fill.
	\return Nothing.
*/
void get_all_paths(path const& file, map<string, map<string, size_t>>& locations) {
	//loading map with every parent directory, the extensions in them and their occurance
	for (auto& i : directory_iterator(file)) {
		locations[absolute(i.path().parent_path()).string()][i.path().extension().string()]++;
	}
}

/*! \fn rvoid get_all_paths(path const& file, map<string, map<string, size_t>>& locations)
	\brief Fills map with every path inside given starting location, the file types
		   they hold and how many of each recursively.
	\param file [in] the path to begin the search.
		   locations [in] the map to fill.
	\return Nothing.
*/
void rget_all_paths(path const& file, map<string, map<string, size_t>>& locations) {
	for (auto& i : recursive_directory_iterator(file)) {
		locations[absolute(i.path().parent_path()).string()][i.path().extension().string()]++;
	}
}

/*! \fn void print_verbose(map<string, map<string, size_t>> folders, 
		vector<pair<string, size_t>>& extsToFind)
	\brief Prints the number of folders containing file types found in the vector,
		   each folder that contains them and how many of that file type are contained
		   in that folder.
	\param folders [in] a map containing every folder, the file types it contains and how many.
		   extsToFind [in] vector containing the desired extensions to print.
	\return Nothing.
*/
void print_verbose(map<string, map<string, size_t>> folders, 
	vector<pair<string, size_t>>& extsToFind) {

	//loading vector with total number of each extension found
	for (auto i = folders.begin(); i != folders.end(); ++i) {
		for (size_t j = 0; j < extsToFind.size(); ++j) {
			if (i->second[extsToFind[j].first] != 0) {
				extsToFind[j].second++;
			}
		}
	}

	//outputting results
	for (size_t i = 0; i < extsToFind.size(); ++i) {
		if (extsToFind[i].second != 0) {
			cout << extsToFind[i].first << " (" << extsToFind[i].second << " folders)\n";
			for (auto& j : folders) {
				if (j.second[extsToFind[i].first] != 0) {
					cout << "\t" << j.second[extsToFind[i].first] << ":\t" << j.first << endl;
				}
			}
			cout << endl;
		}
	}
}

int main(int argc, char* argv[]) {

	//user switches
	bool switches = false;
	bool switchC = false;
	bool switchPlus = false;
	bool switchJava = false;
	bool switchSharp = false;
	bool switchWeb = false;
	bool switchSummary= false;
	bool switchRegex= false;
	bool switchSuppress = false;
	bool switchReverse = false;
	bool switchSizeSort = false;
	bool switchVerbose = false;
	bool switchHelp = false;
	bool printAll = true;

	path start = current_path();

	cout << "fileusage {v1.0.0} (c) 2019, Hunter Bennett\n\n";

	//checking for switches and regex switch in correct position
	if (argc > 1 && argv[1][0] == '-') {
		switches = true;
	}

	//setting designated switches
	if (switches) {
		for (size_t i = 1; i < strlen(argv[1]); ++i) {
			switch (argv[1][i]) {
			case 'c':
				switchC = true;
				printAll = false;
				break;
			case '+':
				switchPlus = true;
				printAll = false;
				break;
			case 'j':
				switchJava = true;
				printAll = false;
				break;
			case '#':
				switchSharp = true;
				printAll = false;
				break;
			case 'w':
				switchWeb = true;
				printAll = false;
				break;
			case 'r':
				switchSuppress = true;
				break;
			case 'R':
				switchReverse = true;
				break;
			case 'S':
				switchSizeSort = true;
				break;
			case 's':
				switchSummary = true;
				break;
			case 'v':
				switchVerbose = true;
				break;
			case 'h':
				switchHelp = true;
				break;
			case 'x':
				switchRegex = true;
				printAll = false;
				//checking for regex switch but no regex
				if (argc == 2) {
					cerr << "Error: regular expression must follox -x switch.\n";
					return EXIT_FAILURE;
				}
				break;
			}
			if (switchHelp)
				break;
		}
	}

	//setting correct arg as the path based on input
	if (!switches && argc >= 2) {
		start = absolute(argv[1]);
		if (!exists(start)) {
			cerr << "Error: folder [" << argv[1] << "] doesn't exist.";
			return EXIT_FAILURE;
		}
	}
	else if (switchRegex && argc >= 4) {
		start = absolute(argv[3]);
		if (!exists(start)) {
			cerr << "Error: folder [" << argv[3] << "] doesn't exist.";
			return EXIT_FAILURE;
		}
	}
	else if (!switchRegex && argc >= 3) {
		start = absolute(argv[2]);
		if (!exists(start)) {
			cerr << "Error: folder [" << argv[2] << "] doesn't exist.";
			return EXIT_FAILURE;
		}
	}
	

	if (switchHelp) {
		cout << "\tUsage: fileusage [-hrRsSvc+#jw(x regularexpression)] "
			<< "[folder]\n";
		cout << "\tswitches:\n";
		cout << "\t\tc\tC files\n";
		cout << "\t\t+\tC++ files\n";
		cout << "\t\tj\tJava files\n";
		cout << "\t\t#\tC# files\n";
		cout << "\t\tw\tWeb files\n";
		cout << "\t\ts\tsummary of the different categories\n";
		cout << "\t\tx\tfilter with a regular expression\n";
		cout << "\t\tr\tsuppress recursive processing of the folder\n";
		cout << "\t\tR\treverse the order of the listing\n";
		cout << "\t\tS\tsort by file sizes\n";
		cout << "\t\tv\tverbose - list all files being scanned\n";
		cout << "\t\th\thelp\n\n";
		cout << "\tfolder\n";
		cout << "\t\tstarting folder or current folder if not provided\n";

		return EXIT_SUCCESS;
	}

	//Map holds each extension, the number of files with that extension, 
	//and their cummulative size
	map<string, pair<size_t, uintmax_t>> extensions;

	//calling correct map filler based on desired recursion
	if (switchSuppress) {
		scan_folder(start, extensions);
	}
	else {
		scan_recursive(start, extensions);
	}

	if (switchVerbose) {
		//map holds file paths, the file types it holds and their occurance
		map<string, map<string, size_t>> verboseExts;

		//vector holds extensions and the number of directories containing them
		vector<pair<string, size_t>> extsToPrint;

		if (switchSuppress)
			get_all_paths(start, verboseExts);
		else
			rget_all_paths(start, verboseExts);

		if (printAll) {
			cout << "All files\n\n";
			for (auto& i : extensions) {
				extsToPrint.push_back(pair<string, size_t>(i.first, 0));
			}
			print_verbose(verboseExts, extsToPrint);
		}
		
		if (switchC) {
			if (extsToPrint.size() > 0)
				extsToPrint.clear();
			cout << "C files\n\n";
			//adding correct file types to vector to query against
			extsToPrint.push_back(pair<string, size_t>(".c", 0));
			extsToPrint.push_back(pair<string, size_t>(".h", 0));
			print_verbose(verboseExts, extsToPrint);
		}

		if (switchPlus) {
			//clearing vector if already added to
			if(extsToPrint.size() > 0)
				extsToPrint.clear();
			cout << "C++ files\n\n";
			extsToPrint.push_back(pair<string, size_t>(".cc", 0));
			extsToPrint.push_back(pair<string, size_t>(".cp", 0));
			extsToPrint.push_back(pair<string, size_t>(".cpp", 0));
			extsToPrint.push_back(pair<string, size_t>(".cxx", 0));
			extsToPrint.push_back(pair<string, size_t>(".c++", 0));
			extsToPrint.push_back(pair<string, size_t>(".hpp", 0));
			extsToPrint.push_back(pair<string, size_t>(".hxx", 0));
			print_verbose(verboseExts, extsToPrint);
		}

		if (switchSharp) {
			if (extsToPrint.size() > 0)
				extsToPrint.clear();
			cout << "C# files\n\n";
			extsToPrint.push_back(pair<string, size_t>(".cs", 0));
			extsToPrint.push_back(pair<string, size_t>(".vb", 0));
			extsToPrint.push_back(pair<string, size_t>(".jsl", 0));
			print_verbose(verboseExts, extsToPrint);
		}

		if (switchJava) {
			if (extsToPrint.size() > 0)
				extsToPrint.clear();
			cout << "Java files\n\n";
			extsToPrint.push_back(pair<string, size_t>(".class", 0));
			extsToPrint.push_back(pair<string, size_t>(".j", 0));
			extsToPrint.push_back(pair<string, size_t>(".jad", 0));
			extsToPrint.push_back(pair<string, size_t>(".jar", 0));
			extsToPrint.push_back(pair<string, size_t>(".java", 0));
			extsToPrint.push_back(pair<string, size_t>(".jsp", 0));
			extsToPrint.push_back(pair<string, size_t>(".ser", 0));
			print_verbose(verboseExts, extsToPrint);
		}

		if (switchWeb) {
			if (extsToPrint.size() > 0)
				extsToPrint.clear();
			cout << "Web files\n\n";
			extsToPrint.push_back(pair<string, size_t>(".htm", 0));
			extsToPrint.push_back(pair<string, size_t>(".html", 0));
			extsToPrint.push_back(pair<string, size_t>(".html5", 0));
			extsToPrint.push_back(pair<string, size_t>(".js", 0));
			extsToPrint.push_back(pair<string, size_t>(".jse", 0));
			extsToPrint.push_back(pair<string, size_t>(".jsc", 0));
			print_verbose(verboseExts, extsToPrint);
		}

		if (switchRegex) {
			if (extsToPrint.size() > 0)
				extsToPrint.clear();
			string regEx = argv[2];
			//string regExExt = regEx.substr(1, regEx.length()); //extraction extension
			string extensionToAdd = ".";
			cout << regEx << "\n\n";
			//extracting all desired extensions from regular expression
			for (size_t i = 0; i < regEx.length(); ++i) {
				if (isalpha(regEx[i])) {
					while (isalpha(regEx[i])) {
						extensionToAdd += regEx[i];
						if (i < regEx.length() - 1)
							++i;
						else
							break;
					}
					extsToPrint.push_back(pair<string, size_t>(extensionToAdd, 0));
					//resetting string
					extensionToAdd = ".";
				}
			}
			print_verbose(verboseExts, extsToPrint);
		}
	}

	regex extsToFind;
	size_t totalFiles; 
	uintmax_t totalSize;

	if (!switches || printAll) {
		cout << "\nAll files: " << absolute(start);
		extsToFind = ".*";
		if (switchSizeSort) {
			print_map_by_size(extensions, switchReverse, extsToFind, totalFiles, totalSize, false);
		}
		else
			print_map(extensions, switchReverse, totalFiles, totalSize, false);
		return EXIT_SUCCESS;
	}
	
	//map holds searched file groupings, their number found and total size
	map<string, pair<size_t, uintmax_t>> summaryMap;

	if (switchC) {
		//setting regex to correct file types
		extsToFind = R"reg(\.(c|h)$)reg";
		//map holds extensions, their occurance and their cumulative size
		map<string, pair<size_t, uintmax_t>> cExts;
		fill_map(extensions, cExts, extsToFind);
		cout << "\nC files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(cExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(cExts, switchReverse, totalFiles, totalSize, false);
		summaryMap["C Files"] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchPlus) {
		extsToFind = R"reg(\.(cc|cp|cpp|cxx|c\x2B\x2B|hpp|hxx)$)reg";
		map<string, pair<size_t, uintmax_t>> plusExts;
		fill_map(extensions, plusExts, extsToFind);
		cout << "\nC++ files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(plusExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(plusExts, switchReverse, totalFiles, totalSize, false);
		summaryMap["C++ Files"] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchSharp) {
		extsToFind = R"reg(\.(cs|vb|jsl)$)reg";
		map<string, pair<size_t, uintmax_t>> sharpExts;
		fill_map(extensions, sharpExts, extsToFind);
		cout << "\nC# files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(sharpExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(sharpExts, switchReverse, totalFiles, totalSize, false);
		summaryMap["C# Files"] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchJava) {
		extsToFind = R"reg(\.(class|j|jad|jar|java|jsp|ser)$)reg";
		map<string, pair<size_t, uintmax_t>> jExts;
		fill_map(extensions, jExts, extsToFind);
		cout << "\nJava files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(jExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(jExts, switchReverse, totalFiles, totalSize, false);
		summaryMap["Java Files"] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchWeb) {
		extsToFind = R"reg(\.(htm|html|html5|js|jse|jsc)$)reg";
		map<string, pair<size_t, uintmax_t>> webExts;
		fill_map(extensions, webExts, extsToFind);
		cout << "\nWeb files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(webExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(webExts, switchReverse, totalFiles, totalSize, false);
		summaryMap["Web Files"] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchRegex) {
		extsToFind = argv[2];
		map<string, pair<size_t, uintmax_t>> userExts;
		fill_map(extensions, userExts, extsToFind);
		cout << "\n" << argv[2] << " files " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(userExts, switchReverse, extsToFind, totalFiles, totalSize, false);
		else
			print_map(userExts, switchReverse, totalFiles, totalSize, false);
		summaryMap[argv[2]] = pair<size_t, uintmax_t>(totalFiles, totalSize);
	}

	if (switchSummary) {
		extsToFind = ".*";
		cout << "\nAll searches " << absolute(start);
		if (switchSizeSort)
			print_map_by_size(summaryMap, switchReverse, extsToFind, totalFiles, totalSize, true);
		else
			print_map(summaryMap, switchReverse, totalFiles, totalSize, true);
	}
}